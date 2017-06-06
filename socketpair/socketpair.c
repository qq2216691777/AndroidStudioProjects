/**
 * 参考：frameworks/native/libs/input/InputTransport.cpp
 * 编译：gcc socketpair.c -o socketpair -lpthread
 * 运行：./socketpair
 * 注明：该程序的缺点是只能在线程中使用和父子进程使用。如果想在不同的进程中使用，则需要把fd告知给另一进程。可以用到binder系统
 */

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>

#define SOCKET_BUFFER_SIZE (32768U)

void *function_thread1( void *arg )
{
	int fd = (int)arg;
	char buf[500]={0};
	int len;
	int cnt = 0;

	while(1)
	{
		/*  */
		len = sprintf(buf,"Hello mian Thread, cnt = %d",cnt++);
		write(fd, buf, len);

		len = read(fd,buf,500);
		buf[len] = 0;
		printf("%s\n",buf );
		sleep(2);

	}
}

int main(int argc, char const *argv[])
{
	int sockets[2];
	/* 创建线程 */
	pthread_t threadID;
	int fd;
	char buf[500]={0};
	int len;
	int cnt = 0;

	int bufferSize = SOCKET_BUFFER_SIZE;

	socketpair(AF_UNIX,SOCK_SEQPACKET, 0, sockets);

	/* 设置缓冲区 */
    setsockopt(sockets[0], SOL_SOCKET, SO_SNDBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[0], SOL_SOCKET, SO_RCVBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[1], SOL_SOCKET, SO_SNDBUF, &bufferSize, sizeof(bufferSize));
    setsockopt(sockets[1], SOL_SOCKET, SO_RCVBUF, &bufferSize, sizeof(bufferSize));

	pthread_create( &threadID, NULL, function_thread1, (void *)sockets[1] );

	fd = sockets[0];
	while(1)
	{
		len = read(fd,buf,500);
		buf[len] = 0;
		printf("%s\n",buf );

		len = sprintf(buf,"Hello Thread1, cnt = %d\n",cnt++);
		write(fd, buf, len);

	}

	return 0;
}