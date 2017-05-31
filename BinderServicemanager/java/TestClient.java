/**
 * 1. get Service
 * 2. 调用服务的 sayhello, sayhello_to
 */
import android.util.Slog;
import android.os.ServiceManager;
import android.os.IBinder;

public class TestClient {
	private static final String TAG = "TestClient";

	public static void main(String[] args) {
		if(args.length == 0 )
		{
			System.out.println("Usage: need parameter: <hello | goodbye >");
			return ;
		}

		if(args[0].equals("hello"))
		{
			/* get Service */
			IBinder binder = ServiceManager.getService("hello");
			if( binder == null )
			{
				System.out.println("can not get hello Service");
				Slog.i(TAG,"can not get hello Service");
				return ;
			}

			IHelloService svr = IHelloService.Stub.asInterface(binder);
			if( args.length == 1 )
			{
				try
				{
					svr.sayhello();
				}catch(Exception e) {}

				System.out.println("Client: call sayhello");
				Slog.i(TAG,"Client: call sayhello");
			}
			else
			{
				try{
					int ret = svr.sayhello_to(args[1]);
					System.out.println("Client: call sayhello_to name = "+args[1]+",cnt="+ret);
					Slog.i(TAG,"Client: call sayhello_to name = "+args[1]+",cnt="+ret);
				}catch(Exception e){}

			}
		}
	}
}