package com.starmicronics.starprntsdk;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class CommonFragment extends Fragment {

    public void setPadding(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            int insetTypes = WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.systemBars();
            Insets bars = insets.getInsets(insetTypes);
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        setPadding(getView());

    }
}
