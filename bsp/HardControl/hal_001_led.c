/*
*   filename hal_001_led.c
*   commod: arm-linux-gcc -shared -o libhardcontrol.so hal_001_led.c -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -fPIC -nostdlib /work/android-5.0.2/prebuilts/ndk/9/platforms/android-19/arch-arm/usr/lib/libc.so  -I /work/android-5.0.2/prebuilts/ndk/9/platforms/android-19/arch-arm/usr/include /work/android-5.0.2/prebuilts/ndk/9/platforms/android-19/arch-arm/usr/lib/liblog.so
*/
#include <jni.h>  /* /usr/lib/jvm/java-1.7.0-openjdk-amd64/include */
#include <android/log.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/ioctl.h>

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

    __android_log_print(ANDROID_LOG_DEBUG,"LEDDemo","led close");
    close(fd);
}


jint ledctrl( JNIEnv *env, jobject cls, jint which, jint status )
{
    int ret;

    ret = ioctl(fd,status,which);
    return 0;
}



/* 2. java <--> C */



static const JNINativeMethod methods[] = {
    {"ledOpen","()I",(void *)ledopen},            /* java function  参数  c function*/
    {"ledClose","()V",(void *)ledclose},
    {"ledCtrl","(II)I",(void *)ledctrl},
};

JNIEXPORT jint JNICALL

JNI_OnLoad( JavaVM *jvm, void *reserved )
{
    JNIEnv *env;
    jclass cls;

    /* 获取运行环境 */
    if((*jvm)->GetEnv(jvm,(void **)&env,JNI_VERSION_1_4))
        return JNI_ERR;

    /* 获取java中的类 */
    cls = (*env)->FindClass(env,"com/example/zws/hardlibrary/HardControl");
    if(cls==NULL)
        return JNI_ERR;

    /* 函数映射 */
    if((*env)->RegisterNatives(env, cls, methods,sizeof(methods)/sizeof(methods[0]))<0)
        return JNI_ERR;

    return JNI_VERSION_1_4;
}

