package com.xposed.v;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import dalvik.system.DexFile;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author V
 * @since 2018/6/20
 */
public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (!"com.c2vl.killer".equals(lpparam.packageName)) return;
        XposedBridge.log("--------------handleLoadPackage--------------");
        DexFile dexFile = null;
        try {
            dexFile = new DexFile(lpparam.appInfo.sourceDir);
        } catch (IOException e) {
            log(e);
        }
        Enumeration<String> entries = dexFile.entries();
        while (entries.hasMoreElements()) {
            String className = entries.nextElement();
            if (isClassNameValid(className, lpparam.packageName)) {
                final Class<?> clazz;
                try {
                    clazz = Class.forName(className, false, lpparam.classLoader);
                } catch (ClassNotFoundException e) {
                    log(e);
                    continue;
                }
                for (Method method : clazz.getDeclaredMethods()) {
                    if (!Modifier.isAbstract(method.getModifiers())) {
                        XposedBridge.hookMethod(method, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                log("HOOKED: " + clazz.getName() + "\\" + param.method.getName());
                            }
                        });
                    }
                }
            }
        }
    }

    public void log(Object str) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        XposedBridge.log("[" + df.format(new Date()) + "]:  "
                + str.toString());
    }

    public boolean isClassNameValid(String className, String packageName) {
        return className.startsWith(packageName)
                && !className.contains("$")
                && !className.contains("BuildConfig")
                && !className.equals(packageName + ".R");
    }
}
