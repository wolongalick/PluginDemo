package com.alick.pluginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PluginMainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_main);

        findViewById(R.id.btn_gotoSecoundActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(that!=null ? that :PluginMainActivity.this,SecoundActivity.class));
            }
        });
    }
}
