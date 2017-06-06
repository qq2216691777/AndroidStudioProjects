/**
 * 参考： frameworks/native/services/inputflinger/EventHub.cpp
 * 编译： gcc -o epoll epoll.c
 * 运行： ./epoll <文件名> <文件名> ...
 * 可以在当前目录下创建一个子目录，用程序监控此目录中的文件，程序可以在后台运行。
 * 例如 tmp
 * ./epoll 1 2  &
 * mkfifo 1 2
 * echo abcd >> 1
 *
 * a. 如果reader以O_RDONLY|O_NONBLOCK打开FIFO文件，
 * 	当writer写入数据时，epoll_wait会立刻返回；
 * 	当writer关闭FIFO之后，reader再次调用epoll_wait，也会立刻返回（原因是:EPOLLHUP）
 *
 * b. 如果reader以O_RDWR打开FIFO文件，
 * 	当writer写入数据时，epoll_wait会立刻返回；
 * 	当writer关闭FIFO之后，reader再次调用epoll_wait, 不会立刻返回，而是继续等待数据
 */

#include <sys/epoll.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>

/* usage: epoll <file1> <file2> ... */

#define EPOLL_MAX_EVENTS	16
#define DATA_MAX_LEN		500

struct epoll_event mPendingEventItems[EPOLL_MAX_EVENTS];



int add_to_epoll( int fd, int epollfd )
{
	struct epoll_event eventItem;
	int res;

    memset(&eventItem, 0, sizeof(eventItem));
    eventItem.events = EPOLLIN ;
    eventItem.data.fd = fd;
    return epoll_ctl(epollfd, EPOLL_CTL_ADD, fd, &eventItem);
}

int remove_from_epoll( int fd, int epollfd )
{
    return epoll_ctl(epollfd, EPOLL_CTL_DEL, fd, NULL );
}

int main(int argc, char const *argv[])
{
	int mEpollFd;
	int i;
	char buf[DATA_MAX_LEN];
	int pollRes;
	int len;

	if (argc<2)
	{
		printf("Usage: %s <file1> <file2> ...\n",argv[0] );
		return -1;
	}

	/* epoll create */
	mEpollFd = epoll_create(8);

	/**
	 * for each file:
	 * open it
	 * add it to epoll: epoll_ctl(...EPOLL_CTL_ADD...)
	 */
	for ( i = 1; i < argc; ++i)
	{
		/* code */
		int tmpFd = open(argv[i], O_RDWR );
		add_to_epoll( tmpFd, mEpollFd);
	}
	/* epoll_wait */
	while(1)
	{
		pollRes = epoll_wait(mEpollFd, mPendingEventItems,EPOLL_MAX_EVENTS,-1);
		for ( i = 0; i < pollRes; ++i)
		{
			len = read(mPendingEventItems[i].data.fd, buf, DATA_MAX_LEN);
			buf[len] = 0;
			printf("file%d get data:%s", i,buf);
		}
	}

	return 0;
}