package com.smallcat.theworld;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.smallcat.theworld.ui.activity.SplashActivity;
import com.smallcat.theworld.utils.LogUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import org.litepal.LitePal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hui
 * @date 2018/8/9
 */
public class App extends Application{

    private static App mApplication;

    private List<Activity> mActivists = Collections.synchronizedList(new LinkedList<>());

    public static App getInstance() {
        return mApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        registerActivityListener();

        //app字体大小不随系统变化
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());

        CrashReport.initCrashReport(getApplicationContext(), "99e89589c4", false);
        initX5WebView();
        LitePal.initialize(this);
    }

    private void initX5WebView(){
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.e("x5", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                removeActivity(activity);
            }
        });
    }

    public void addActivity(Activity act) {
        if (act != null){
            mActivists.add(act);
        }
    }

    public void removeActivity(Activity act) {
        if (act != null){
            mActivists.remove(act);
        }
    }

    public void finishAllActivity() {
        if (mActivists != null) {
            for (Activity act : mActivists) {
                act.finish();
            }
        }
    }

    public void finishAllActivityExcept(Activity activity) {
        if (mActivists != null) {
            for (Activity act : mActivists) {
                if (act != activity) {
                    act.finish();
                }
            }
        }
    }

    /**
     * 重新初始化应用界面，清空当前Activity棧，并启动欢迎页面
     */
    public static void reInitApp() {
        Intent intent = new Intent(mApplication, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mApplication.startActivity(intent);
    }

    public void finishActivity(Activity activity) {
        if (mActivists == null || mActivists.isEmpty()) {
            return;
        }
        if (activity == null) {
            return;
        }
        removeActivity(activity);
        activity.finish();
    }

    public void exitApp() {
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
