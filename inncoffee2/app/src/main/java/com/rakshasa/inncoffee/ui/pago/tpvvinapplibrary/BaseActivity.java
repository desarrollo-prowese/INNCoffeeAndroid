package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary;

import android.os.Bundle;

import com.rakshasa.inncoffee.R;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements BaseView {
    public void finishActivity() {
    }

    public void showResult(String str) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_base);
    }
}
