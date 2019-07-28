package com.wenchao.superrouter;

import android.app.Application;

import com.wenchao.router.ARouter;

/**
 * @author wenchao
 * @date 2019/7/27.
 * @time 22:52
 * description：
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
