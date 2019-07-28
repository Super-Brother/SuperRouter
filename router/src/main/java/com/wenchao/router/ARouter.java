package com.wenchao.router;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * @author wenchao
 * @date 2019/7/27.
 * @time 22:35
 * description：中间人 代理
 */
public class ARouter {

    /**
     * 装载所有的activity的类对象的容器
     */
    private Map<String, Class<? extends Activity>> activityList;
    /**
     * 上下文
     */
    private Context context;

    @SuppressLint("StaticFieldLeak")
    private static ARouter router = new ARouter();

    private ARouter() {
        activityList = new HashMap<>();
    }

    public static ARouter getInstance() {
        return router;
    }

    public void init(Application application) {
        this.context = application;
        List<String> classNames = getClassName("com.netease.util");
        for (String className : classNames) {
            try {
                Class<?> aClass = Class.forName(className);
                //判断这个类是否是IRouter的实现类
                if(IRouter.class.isAssignableFrom(aClass)){
                    IRouter iRouter = (IRouter) aClass.newInstance();
                    iRouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param packageName
     * @return
     */
    private List<String> getClassName(String packageName) {
        //创建一个class对象的集合
        List<String> classList = new ArrayList<>();
        String path = null;
        try {
            //通过包管理器   获取到应用信息类然后获取到APK的完整路径
            path = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), 0).sourceDir;
            //根据APK的完整路径获取到编译后的dex文件
            DexFile dexfile = new DexFile(path);
            // 获得编译后的dex文件中的所有class
            Enumeration entries = dexfile.entries();
            //然后进行遍历
            while (entries.hasMoreElements()) {
                //通过遍历所有的class的包名
                String name = (String) entries.nextElement();
                // 判断类的包名是否符合
                if(name.contains(packageName)){
                    //如果符合 就添加到集合中
                    classList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classList;
    }

    public void putActivity(String path, Class<? extends Activity> clazz) {
        if (path != null && clazz != null) {
            activityList.put(path, clazz);
        }
    }

    public void jumpActivity(String path, Bundle bundle) {
        final Class<? extends Activity> clazz = activityList.get(path);
        if (clazz == null) {
            return;
        }
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

}
