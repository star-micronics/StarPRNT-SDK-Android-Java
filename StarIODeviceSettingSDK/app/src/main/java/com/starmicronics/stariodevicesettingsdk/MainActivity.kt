package com.starmicronics.stariodevicesettingsdk

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.starmicronics.stariodevicesetting.StarIODeviceSettingException
import com.starmicronics.stariodevicesetting.StarNetworkManager
import com.starmicronics.stariodevicesetting.StarNetworkSetting
import com.starmicronics.stariodevicesetting.SteadyLanSetting
import com.starmicronics.stariodevicesettingsdk.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 1000
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Edge to Edge
        setPadding(view)

        val title = getString(R.string.app_name) + " V" + getVersionName(baseContext)
        setTitle(title)

        binding.loadButton.setOnClickListener {
            scope.launch {
                try {
                    val manager = StarNetworkManager(binding.portNameEditText.text.toString(), baseContext) //Please refer to the SDK manual for portName argument which using for communicating with the printer.(https://www.star-m.jp/products/s_print/sdk/starprnt_sdk/manual/android_java/en/api_stario_port.html#getport)

                    val result = manager.load()

                    showResultDialog("SteadyLAN Setting : ${result.steadyLanSetting.name}")
                } catch (e: StarIODeviceSettingException) {
                    showResultDialog(e.message)
                }
            }
        }

        binding.applyButton.setOnClickListener {
            scope.launch {
                try {
                    val manager = StarNetworkManager(binding.portNameEditText.text.toString(), baseContext) //Please refer to the SDK manual for portName argument which using for communicating with the printer.(https://www.star-m.jp/products/s_print/sdk/starprnt_sdk/manual/android_java/en/api_stario_port.html#getport)

                    val setting = StarNetworkSetting()

                    setting.steadyLanSetting = when (binding.radioGroup.checkedRadioButtonId) {
                        R.id.radioButton -> SteadyLanSetting.Disable
                        R.id.radioButton2 -> SteadyLanSetting.iOS
                        R.id.radioButton3 -> SteadyLanSetting.Android
                        R.id.radioButton4 -> SteadyLanSetting.Windows
                        R.id.radioButton5 -> SteadyLanSetting.Unspecified
                        else -> SteadyLanSetting.Unspecified
                    }

                    manager.apply(setting)

                    showResultDialog("Data transmission succeeded.\nPlease confirm the current settings by Load method after a printer reset is executed.")
                } catch (e: StarIODeviceSettingException) {
                    showResultDialog(e.message)
                }
            }
        }

        // If you are using Android 12 or later and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions

        // If you are using Android 17 or later and target SdkVersion is 37 or later,
        // you have to request ACCESS_LOCAL_NETWORK permission to use the LAN printer.
        // https://developer.android.com/privacy-and-security/local-network-permission
        requestRuntimePermissions()
    }

    private suspend fun showResultDialog(message: String?) {
        withContext(Dispatchers.Main) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Communication Result")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    // Do Nothing
                }
                .show()
        }
    }

    private fun getVersionName(context: Context): String? {
        var versionName = ""
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            // do nothing
        }
        return versionName
    }

    private fun requestRuntimePermissions() {
        val permissions = mutableListOf<String>()

        // Android 12 or later : Bluetooth
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (applicationContext.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            }

            if (applicationContext.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
                permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            }
        }

        // Android 17 or later : LAN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CINNAMON_BUN) {
            if (applicationContext.checkSelfPermission(Manifest.permission.ACCESS_LOCAL_NETWORK) == PackageManager.PERMISSION_DENIED) {
                permissions.add(Manifest.permission.ACCESS_LOCAL_NETWORK)
            }
        }

        if (permissions.isNotEmpty()) {
            requestPermissions(permissions.toTypedArray(), PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            var allGranted = true

            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false
                    break
                }
            }

            if (!allGranted) {
                val text = "You have to check permissions to use the LAN / Bluetooth printer"
                Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setPadding(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or
                        WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }
    }
}