/* Copyright 2008 The Android Open Source Project
 */

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <linux/types.h>
#include <stdbool.h>
#include <string.h>

#include <private/android_filesystem_config.h>

#include "binder.h"
#include "test_server.h"



uint32_t svcmgr_lookup(struct binder_state *bs, uint32_t target, const char *name)
{
    uint32_t handle;
    unsigned iodata[512/4];
    struct binder_io msg, reply;

    bio_init(&msg, iodata, sizeof(iodata), 4);
    bio_put_uint32(&msg, 0);  // strict mode header
    bio_put_string16_x(&msg, SVC_MGR_NAME);
    bio_put_string16_x(&msg, name);

    if (binder_call(bs, &msg, &reply, target, SVC_MGR_CHECK_SERVICE))
        return 0;

    handle = bio_get_ref(&reply);

    if (handle)
        binder_acquire(bs, handle);

    binder_done(bs, &msg, &reply);

    return handle;
}


struct binder_state *g_bs;
uint32_t g_handle;

void sayhello(void)
{
    unsigned iodata[512/4];
    struct binder_io msg, reply;

	/* ����binder_io */
    bio_init(&msg, iodata, sizeof(iodata), 4);
    bio_put_uint32(&msg, 0);  // strict mode header

	/* ������� */

	/* ����binder_call */
    if (binder_call(g_bs, &msg, &reply, g_handle, HELLO_SVR_CMD_SAYHELLO))
        return ;
	
	/* ��reply�н���������ֵ */

    binder_done(g_bs, &msg, &reply);
	
}

int sayhello_to(char *name)
{
	unsigned iodata[512/4];
	struct binder_io msg, reply;
	int ret;

	/* ����binder_io */
	bio_init(&msg, iodata, sizeof(iodata), 4);
	bio_put_uint32(&msg, 0);  // strict mode header

	/* ������� */
    bio_put_string16_x(&msg, name);

	/* ����binder_call */
	if (binder_call(g_bs, &msg, &reply, g_handle, HELLO_SVR_CMD_SAYHELLO_TO))
		return 0;

	/* ��reply�н���������ֵ */
	ret = bio_get_uint32(&reply);

	binder_done(g_bs, &msg, &reply);

	return ret;

}



/* ./test_client hello
 * ./test_client hello <name>
 */

int main(int argc, char **argv)
{
    int fd;
    struct binder_state *bs;
    uint32_t svcmgr = BINDER_SERVICE_MANAGER;
    uint32_t handle;
	int ret;

	if (argc==1)
	{
		fprintf(stderr, "Usage:\n" );
		fprintf(stderr, "%s hello\n",argv[0] );
		fprintf(stderr, "%s hello <name>\n", argv[0]);
		return -1;
	}

    bs = binder_open(128*1024);
    if (!bs) {
        fprintf(stderr, "failed to open binder driver\n");
        return -1;
    }
	g_bs = bs;

	/* get service */
	handle = svcmgr_lookup(bs, svcmgr, "hello");
	if (!handle) {
        fprintf(stderr, "failed to get hello service\n");
        return -1;
	}
	g_handle = handle;

	/* send data to server */
	if (argc == 2) {
		sayhello();
	} else if (argc == 3) {
		ret = sayhello_to(argv[2]);
        fprintf(stderr, "client get = %d\n", ret);
	}

	binder_release(bs, handle);

    return 0;
}
