/**
 * 1. add Service
 * 2. while(true) { read data, prase data, call function, reply}
 */
import android.util.Slog;
import android.os.ServiceManager;

public class TestServer {
	private static final String TAG = "TestServer";

	public static void main(String[] args) {
		/* add Service */
		Slog.i(TAG,"add Hello Service");
		ServiceManager.addService("hello", new HelloService() );

		while(true)
		{
			try
			{
				Thread.sleep(100);
			}
			catch( Exception e )
			{

			}
		}
	}
}