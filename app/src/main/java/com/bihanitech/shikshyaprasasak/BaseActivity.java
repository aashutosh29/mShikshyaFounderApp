package com.bihanitech.shikshyaprasasak;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //tests
    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        //condifition

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            // update overrideConfiguration with your locale
//            setLocale(overrideConfiguration) // you will need to implement this
            if (overrideConfiguration != null) {
                int uiMode = overrideConfiguration.uiMode;
                overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
                overrideConfiguration.uiMode = uiMode;
            }
        }

        //end condition here
        super.applyOverrideConfiguration(overrideConfiguration);
    }
}
