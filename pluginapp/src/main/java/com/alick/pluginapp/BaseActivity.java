package com.alick.pluginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alick.pluginlibrary.IntentKey;
import com.alick.pluginlibrary.PluginInterface;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2019/1/6
 * 备注:
 */
public class BaseActivity extends Activity implements PluginInterface {
    private static final String TAG = "BaseActivity";
    protected Activity that;

    /**
     * 注入activity
     *
     * @param proxyActivity
     */
    @Override
    public void attach(Activity proxyActivity) {
        that=proxyActivity;
    }

    @Override
    public void startActivity(Intent intent) {
        if(that!=null){
            Intent proxyiIntent = new Intent(that,that.getClass());
            proxyiIntent.putExtra(IntentKey.ACTIVITY_FULL_NAME,intent.getComponent().getClassName());
            that.startActivity(proxyiIntent);
        }else {
           super.startActivity(intent);
        }
    }

    @Override
    public void setContentView(View view) {
        if(that!=null){
            that.setContentView(view);
        }else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if(that!=null){
            that.setContentView(layoutResID);
        }else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if(that!=null){
            return that.findViewById(id);
        }else {
            return super.findViewById(id);
        }
    }


    @Override
    public Intent getIntent() {
        if(that!=null){
            return that.getIntent();
        }else {
            return super.getIntent();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if(that!=null){
            return that.getClassLoader();
        }else {
            return super.getClassLoader();
        }
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        if(that!=null){
            return that.getLayoutInflater();
        }else {
            return super.getLayoutInflater();
        }
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if(that!=null){
            return that.getApplicationInfo();
        }else {
           return super.getApplicationInfo();
        }
    }

    @Override
    public Window getWindow() {
        if(that!=null){
            return that.getWindow();
        }else {
            return super.getWindow();
        }
    }

    @Override
    public WindowManager getWindowManager() {
        if(that!=null){
            return that.getWindowManager();
        }else {
            return super.getWindowManager();
        }
    }

    @Override
    public void finish() {
        if(that!=null){
            that.finish();
        }else {
            super.finish();
        }
    }

    @Override
    public Resources getResources() {
        if(that!=null){
            return that.getResources();
        }else {
            return super.getResources();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            String testKey = savedInstanceState.getString("testKey");
            Log.i(TAG,"从proxyActivity传来的数据:"+testKey);
        }

        if(that==null){
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        if(that==null){
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if(that==null){
            super.onRestart();
        }
    }

    @Override
    public void onResume() {
        if(that==null){
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if(that==null){
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if(that==null){
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if(that==null){
            super.onDestroy();
        }
    }

}
