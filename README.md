#  AndroidStudioProjects

## 2017-5-24  16:10      完成了在TINY4412上通过app控制核心板上的4个led灯的亮灭

## 2017-5-24  21:10  V2  完成了JNI 没有app和HAL

## 2017-5-24  22:00  V3  完成了app的编写和调试，app能通过服务来控制硬件

## 2017-5-25  15:30  V4  完成了HAL的编写，使app通过反射来访问硬件服务(可以不导入classes.jar)

## 2017-5-26  15:00  V5  新添了一个App_Addons_001_Message工程，实现了一个线程间通信的app

## 2017-5-26  22:40  V6  学习了Linux的led_class驱动，并实现了Linux系统的led_class驱动 leds_4412.c
####			   需要重新配置内核  [*]LED Class Support  [*]LED Trigger support [*]LED Timer Trigger

## 2017-5-27  17:30  V7  实现了通知灯、电源灯和屏幕背光功能。（其中添加了Android.mk lights.c文件）

## 2017-5-27  23:30  V8  添加了一个控制通知灯的app工程(App_Addons_002_LightDemo)。

## 2017-5-28  10:00  V9  在app工程中(App_Addons_002_LightDemo)，添加了对背光控制的功能。

## 2017-5-28  10:00  V10 新添Binder系统案例程序(BinderServicemanager)

## 2017-5-29  09:00  V11 进一步完善了Binder系统案例程序(BinderServicemanager)

## 2017-5-29  22:00  V12 完善了代码，BinderServicemanager支持多线程

## 2017-5-30  12:00  V13 增加了一套BinderServicemanager的C++代码，同时C++程序和C程序能够通用（运行在Android系统）

## 2017-5-31  11:30  V14 程序中增加了一个goodbye服务 （BinderServicemanager）