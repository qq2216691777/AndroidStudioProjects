/* 实现 Hello服务的函数 */

import android.util.Slog;

public class HelloService extends IHelloService.Stub {
	private static final String TAG = "HelloService";
	private int cnt = 0;

	public void sayhello() throws android.os.RemoteException {		/* Copy from IHelloService.java */
		cnt ++;
		Slog.i(TAG," Service: sayhello, cnt = "+cnt);
	}
	public int sayhello_to(java.lang.String name) throws android.os.RemoteException {  /* Copy from IHelloService.java */
		cnt ++;
		Slog.i(TAG,"Service: sayhello_to, cnt = "+cnt+",name = "+name);
		return cnt;
	}
}