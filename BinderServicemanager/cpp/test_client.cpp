/* 参考C代码 test_client */
/* 参考C++代码 test_service.cpp */

#include <fcntl.h>
#include <sys/prctl.h>
#include <sys/wait.h>
#include <binder/IPCThreadState.h>
#include <binder/ProcessState.h>
#include <binder/IServiceManager.h>
#include <cutils/properties.h>
#include <utils/Log.h>

#include "IHelloService.h"


using namespace android;


int main(int argc, char const *argv[])
{
	int ret=0;
	if (argc==1)
	{
		ALOGI("Usage:" );
		ALOGI("%s hello",argv[0] );
		ALOGI("%s hello <name>", argv[0]);
		return -1;
	}
	/* get service */
	/* 打开驱动 */
	sp<ProcessState> proc(ProcessState::self());

	/* 获得BpServiceManager */
    sp<IServiceManager> sm = defaultServiceManager();


	sp<IBinder> binder = sm->getService(String16("hello"));
	if(binder == 0 )
	{
		ALOGI("Can't get Service\n" );
		return -1;
	}
	sp<IHelloService> service = interface_cast<IHelloService>(binder);

	if(argc<3)
	{
		service->sayhello();
		ALOGI("Client call sayhello" );
	}
	else
	{
		ret = service->sayhello_to(argv[2]);
		ALOGI("Client call sayhello_to  %s, ret = %d",argv[2],ret );
	}



	return 0;

}
