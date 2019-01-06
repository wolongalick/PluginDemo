package com.alick.hostapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 功能: 插件管理器
 * 作者: 崔兴旺
 * 日期: 2019/1/6
 * 备注:
 */
public class PluginManager {
    private static PluginManager instance = null;

    private Context context;
    private Resources resources;
    private DexClassLoader dexClassLoader;
    private PackageInfo packageInfo;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager();
                }
            }
        }
        return instance;
    }


    public void init(Context context) {
        this.context = context;
    }

    public void loadApk(String path){
        //设置dex优化后的换成目录(优化后的文件叫作odex)
        File dexOutfile=context.getDir("dex",Context.MODE_PRIVATE);
        //取得packageInfo
        packageInfo=context.getPackageManager().getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);
        //从sd卡中的apk取得dexClassLoader
        dexClassLoader=new DexClassLoader(path,dexOutfile.getAbsolutePath(),null,context.getClassLoader());
        try {
            AssetManager assetManager=AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath",String.class);
            method.invoke(assetManager,path);
            //从sd卡中的apk取得resources
            resources=new Resources(assetManager,context.getResources().getDisplayMetrics(),context.getResources().getConfiguration());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}
