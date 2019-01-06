package com.alick.hostapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.alick.pluginlibrary.IntentKey;
import com.alick.pluginlibrary.PluginInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2019/1/6
 * 备注:
 */
public class ProxyActivity extends Activity {
    private String activityFullName;
    private PluginInterface pluginInterface;    //被代理的activity实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFullName=getIntent().getStringExtra(IntentKey.ACTIVITY_FULL_NAME);

        try {
            Class<?> activityClass = getClassLoader().loadClass(activityFullName);
            Constructor<?> constructor = activityClass.getConstructor();
            Object object = constructor.newInstance();

            //由于传来的activity路径对应的activity必须实现PluginInterface接口,因此直接强转即可
            pluginInterface= (PluginInterface) object;
            //将宿主中的代理activity注入,传到插件apk中
            pluginInterface.attach(this);

            Bundle bundle=new Bundle();
            bundle.putString("testKey","呵呵呵");//传入intent参数
            pluginInterface.onCreate(bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        String activityFullName = intent.getStringExtra(IntentKey.ACTIVITY_FULL_NAME);
        Intent proxyiIntent=new Intent(this,ProxyActivity.class);
        proxyiIntent.putExtra(IntentKey.ACTIVITY_FULL_NAME,activityFullName);
        super.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pluginInterface.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pluginInterface.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pluginInterface.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pluginInterface.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pluginInterface.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pluginInterface.onDestroy();
    }


    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

}
