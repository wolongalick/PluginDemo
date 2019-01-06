package com.alick.hostapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.alick.pluginlibrary.IntentKey;

import java.io.File;

public class MainActivity extends Activity {
    private final int REQUEST_PERMISSION_SDCARD=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PluginManager.getInstance().init(this);
        PluginManager.getInstance().loadApk(new File(Environment.getExternalStorageDirectory(),"pluginapp-debug.apk").getAbsolutePath());
    }

    public void onClickBtn(View view) {
        if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
            gotoPluginApp();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION_SDCARD);
            }
        }
    }

    /**
     * 跳转到插件app的主页面
     */
    private void gotoPluginApp() {
        Intent intent=new Intent(this,ProxyActivity.class);
        intent.putExtra(IntentKey.ACTIVITY_FULL_NAME,PluginManager.getInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
    }


    /**
     * 检查权限
     * @param permission
     * @return
     */
    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int targetSdkVersion = 0;
            try {
                final PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
                targetSdkVersion = info.applicationInfo.targetSdkVersion;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        //由于设备版本低于android6.0,所以权限默认是被允许的
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_PERMISSION_SDCARD){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                gotoPluginApp();
            }else {
                Toast.makeText(this,"请开启读取SD卡权限",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
