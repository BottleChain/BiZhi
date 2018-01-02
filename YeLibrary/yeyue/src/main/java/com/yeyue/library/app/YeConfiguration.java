package com.yeyue.library.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.ConfigModule;
import com.kingja.loadsir.core.LoadSir;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.yeyue.library.constant.YeConstant;
import com.yeyue.library.widgets.callback.YeEmptyCallback;
import com.yeyue.library.widgets.callback.YeErrorCallback;
import com.yeyue.library.widgets.callback.YeLoadingCallback;
import com.yeyue.library.widgets.callback.YeTimeoutCallback;
import com.yeyue.library.widgets.imageload.glide.YeGlideImageLoaderStrategy;
import com.yeyue.library.widgets.skin.loader.YeSkinSDCardLoader;

import java.net.Proxy;
import java.util.List;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;
import timber.log.Timber;

/**
 * app的全局配置信息在此配置,需要将此实现类声明到AndroidManifest中
 * Created by jess on 12/04/2017 17:25
 * Contact with jess.yan.effort@gmail.com
 */


public  class YeConfiguration implements ConfigModule {


    /**
     * 使用{@link GlobalConfigModule.Builder}给框架配置一些配置参数
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        //重构图片框架，为图片框架添加多种功能
        builder.imageLoaderStrategy(new YeGlideImageLoaderStrategy());
    }



    /**
     * 使用{@link AppLifecycles}在Application的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     * @return
     */
    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecycles() {
            @Override
            public void attachBaseContext(Context context) {

            }

            @Override
            public void onCreate(Application application) {
                //全局配置默认状态页
                AppComponent appComponent = ((BaseApplication) application).getAppComponent();
                appComponent.extras().put(YeConstant.LoadSir.ERROR,new YeErrorCallback());
                appComponent.extras().put(YeConstant.LoadSir.EMPTY,new YeEmptyCallback());
                appComponent.extras().put(YeConstant.LoadSir.TIMEOUT,new YeTimeoutCallback());
                appComponent.extras().put(YeConstant.LoadSir.LOADING,new YeLoadingCallback());
                LoadSir.beginBuilder()
                        .addCallback(new YeErrorCallback())
                        .addCallback(new YeEmptyCallback())
                        .addCallback(new YeTimeoutCallback())
                        .addCallback(new YeLoadingCallback())
                        .commit();

                //初始化自定义换肤
                SkinCompatManager.withoutActivity(application)
                        .addInflater(new com.yeyue.library.widgets.skin.bottombar.app.SkinMaterialViewInflater())
                        .addInflater(new SkinMaterialViewInflater())    // material design
                        .addInflater(new SkinConstraintViewInflater())  // ConstraintLayout
                        .addInflater(new SkinCardViewInflater())        // CardView v7
                        .addStrategy(new YeSkinSDCardLoader())          // 自定义加载策略，指定SDCard路径
                        .setSkinStatusBarColorEnable(false)
                        .loadSkin();

                FileDownloader.setupOnApplicationOnCreate(application)
                        .connectionCreator(new FileDownloadUrlConnection
                                .Creator(new FileDownloadUrlConnection.Configuration()
                                .connectTimeout(15_000) // set connection timeout.
                                .readTimeout(15_000) // set read timeout.
                                .proxy(Proxy.NO_PROXY) // set proxy
                        ))
                        .commit();
            }

            @Override
            public void onTerminate(Application application) {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Timber.w(activity + " - onActivityCreated");
                //设置状态栏全透明
                StatusBarUtil.setTransparent(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                //Timber.w(activity + " - onActivityStarted");
                //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里

            }

            @Override
            public void onActivityResumed(Activity activity) {
                //Timber.w(activity + " - onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                //Timber.w(activity + " - onActivityPaused");
                //activity隐藏的时候，回收glide内存
            }

            @Override
            public void onActivityStopped(Activity activity) {
                //Timber.w(activity + " - onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                //Timber.w(activity + " - onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                //Timber.w(activity + " - onActivityDestroyed");
            }
        });
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);

            }

            @Override
            public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {

            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            }
        });
    }
}
