/*** * lights.c * ***/

复制 Android.mk lights.c 到 /work/android-5.0.2/hardware/libhardware/modules/lights/  目录中

修改 vi vendor/friendly-arm/tiny4412/device-tiny4412.mk
去掉lights相关的选项 (3行)

mmm hardware/libhardware/modules/lights/
make snod

比较两个文件是否相同
diff vendor/friendly-arm/tiny4412/proprietary/lights.tiny4412.so out/target/product/tiny4412/obj/lib/lights.tiny4412.so

修改内核 drivers/leds/led-class.c drivers/leds/ledtrig-timer.c  将0644 改为0666


logcat lights:V *:S