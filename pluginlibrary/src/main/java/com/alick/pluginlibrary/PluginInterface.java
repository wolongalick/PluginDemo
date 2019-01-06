package com.alick.pluginlibrary;

import android.app.Activity;
import android.os.Bundle;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2019/1/6
 * 备注:
 */
public interface PluginInterface {
    /**
     * 注入activity
     * @param proxyActivity
     */
    void attach(Activity proxyActivity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
