
#define LOG_TAG "LedService"

#include "jni.h"
#include "JNIHelp.h"
#include "android_runtime/AndroidRuntime.h"

#include <utils/misc.h>
#include <utils/Log.h>
#include <hardware_legacy/vibrator.h>

#include <stdio.h>
#include <android/log.h>
#include <fcntl.h>
#include <sys/types.h>


namespace android
{

    static jint fd;

    jint ledopen( JNIEnv *env, jobject cls )
    {
        fd = open("/dev/leds",O_RDWR);
        if(fd>=0)
            return 0;
        else
            return -1;

    }

    void ledclose( JNIEnv *env, jobject cls )
    {

    //    ALOGI("led close");
        close(fd);
    }


    jint ledctrl( JNIEnv *env, jobject cls, jint which, jint status )
    {
        int ret;

        ret = ioctl(fd,status,which);
        return 0;
    }

    static const JNINativeMethod method_table[] = {
    {"native_ledOpen","()I",(void *)ledopen},            /* java function  参数  c function*/
    {"native_ledClose","()V",(void *)ledclose},
    {"native_ledCtrl","(II)I",(void *)ledctrl},
};



    int register_android_server_LedService(JNIEnv *env)
    {
        return jniRegisterNativeMethods(env, "com/android/server/LedService",
                method_table, NELEM(method_table));
    }

};