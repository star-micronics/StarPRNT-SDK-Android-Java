package com.starmicronics.stariodevicesettingsdk

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.starmicronics.stariodevicesetting.StarIODeviceSettingException
import com.starmicronics.stariodevicesetting.StarNetworkManager
import com.starmicronics.stariodevicesetting.StarNetworkSetting
import com.starmicronics.stariodevicesetting.SteadyLanSetting
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = getString(R.string.app_name) + " V" + getVersionName(baseContext)
        setTitle(title)

        loadButton.setOnClickListener {
            scope.launch {
                try {
                    val manager = StarNetworkManager(portNameEditText.text.toString(), baseContext) //Please refer to the SDK manual for portName argument which using for communicating with the printer.(https://www.star-m.jp/products/s_print/sdk/starprnt_sdk/manual/android_java/en/api_stario_port.html#getport)

                    val result = manager.load()

                    showResultDialog("SteadyLAN Setting : ${result.steadyLanSetting.name}")
                } catch (e: StarIODeviceSettingException) {
                    showResultDialog(e.message)
                }
            }
        }

        applyButton.setOnClickListener {
            scope.launch {
                try {
                    val manager = StarNetworkManager(portNameEditText.text.toString(), baseContext) //Please refer to the SDK manual for portName argument which using for communicating with the printer.(https://www.star-m.jp/products/s_print/sdk/starprnt_sdk/manual/android_java/en/api_stario_port.html#getport)

                    val setting = StarNetworkSetting()

                    setting.steadyLanSetting = when (radioGroup.checkedRadioButtonId) {
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
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            // do nothing
        }
        return versionName
    }
}