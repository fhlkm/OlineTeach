package com.china.online.teach;

import android.app.Application;

/**
 * Created by hanlu.feng on 9/14/2017.
 */

public class CommonLib {
    private static Application sApp;
    public static Application getApplication(){
        return sApp;
    }

    public static Application getsApp() {
        return sApp;
    }

    public static void setsApp(Application sApp) {
        CommonLib.sApp = sApp;
    }

}
