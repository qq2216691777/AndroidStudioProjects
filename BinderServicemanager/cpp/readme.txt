将文件放入  framework/testing目录下


 su
 busybox mount -t nfs -o nolock,vers=2 192.168.1.108:/work /mnt
 （必须加busybox，因为android中的mount命令不支持nfs，所以需要用busybox中的mount命令）

out/target/product/tiny4412/system/bin

c++实现的test_server和c实现的test_client是通用的 反过来也是一样的。
因为有了android系统，所以不要再运行servermanage程序

进一步完善了系统。新增了一个goodbye服务。
