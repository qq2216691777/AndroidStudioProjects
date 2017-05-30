

system/core/include/private/android_filesystem_capability.h
system/core/include/private/android_filesystem_config.h

bionic/libc/kernel/uapi/linux/binder.h


直接make
然后运行 ./service_manager    ./test_server    ./test_client
可以实现 由./test_client 远程调用 ./test_server 中的函数 并传递数据

完善了此程序

binder驱动程序目录在 linux源代码中的 drivers/staging/android/binder.c

完善了代码。支持多线程
