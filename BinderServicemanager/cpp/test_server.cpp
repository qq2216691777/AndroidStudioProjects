/* 参考： frameworks/av/media/mediaserver/main_mediaserver.cpp  */

#include "IHelloService.h"
#include "IGoodbyeService.h"

#include <fcntl.h>
#include <sys/prctl.h>
#include <sys/wait.h>
#include <binder/IPCThreadState.h>
#include <binder/ProcessState.h>
#include <binder/IServiceManager.h>
#include <cutils/properties.h>
#include <utils/Log.h>


using namespace android;


int main(int argc, char const *argv[])
{
	/* addService */

	/* loop: read data, prashe data , call function */

	/* 打开驱动 */
	sp<ProcessState> proc(ProcessState::self());
	ALOGI("open device ");
	/* 获得BpServiceManager */
    sp<IServiceManager> sm = defaultServiceManager();

    sm->addService(String16("hello"), new BnHelloService());
    sm->addService(String16("goodbye"), new BnGoodbyeService());

    /* 循环体 */
    ProcessState::self()->startThreadPool();
    IPCThreadState::self()->joinThreadPool();

    return 0;

}
