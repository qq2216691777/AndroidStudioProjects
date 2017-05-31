
(1) 把 IHelloService.aidl 文件放入
		frameworks/base/core/java/android/os

(2) 修改 frameworks/base/Android.mk
		添加一行 core/java/android/os/IHelloService.aidl

(3) mmm frameworks/base

(4) 他会生成： out/target/common/obj/JAVA_LIBRARIES/framework_intermediates/src/core/java/android/os/IHelloService.java 文件

//如何用java实现hello服务

2.1 写接口文件
	写.aidl文件， 上传、编译，得到IHelloService.java
	里面有 Stub : onTransact， 它会分辨接收到数据然后调用sayhello和sayhello_to
		Proxy：提供有sayhello，sayhello_to 两个函数，他们会构造数据然后发送给server

2.2 实现服务类：HelloService.java
	在里面定义 sayhello，sayhello_to

2.3 实现APP: TestServer, TestClient
	TestServer: addService 循环
	TestClient： getservice 调用sayhello，sayhello_to（来自proxy）

busybox mount -t nfs -o nolock,vers=2 192.168.1.108:/work /mnt

logcat TestServer:* TestClient:* HelloService:* *:S &
CLASSPATH=TestServer.jar app_process / TestServer &
