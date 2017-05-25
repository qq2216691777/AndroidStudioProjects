package com.android.server;

import android.util.Slog;
import android.os.ILedService; /*来自自动生成的文件*/

public class LedService extends ILedService.Stub {
    private static final String TAG = "LedService";

    /* call native c function to access hardware */

    public int ledCtrl(int which, int status) throws android.os.RemoteException /* 此函数名是从自动生成的java文件ILedService.java中拷贝而来的 */
    {
        return native_ledCtrl(which,status);
    }

    public LedService(){
    	native_ledOpen();
    }

    public native int native_ledCtrl(int which, int status);
    public native int native_ledOpen();
    public native void native_ledClose();

}
