package top.lmissw.app.app_addons_001_message;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.os.HandlerThread;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Led_001_Message";
    private Button mButton;
    private int ButtonCount=0;
    private  Thread myThread;
    private  MyThread myThread2;

    private Handler mHandler;

    private HandlerThread myThread3;
    private Handler mHandler3;

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            int count=0;
            for (; ; )
            {
                Log.d(TAG,"myThread "+count);
                count++;

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyThread extends Thread {
        private  Looper mLooper;
        /* 具有处理消息功能的线程 */
        public void run()
        {
            super.run();
            Looper.prepare();
            synchronized (this) {
                mLooper = Looper.myLooper();
                notifyAll();
            }
            Looper.loop();      //具有处理线程消息的功能
        }

        public Looper getLooper()
        {
            if(!isAlive()) {
                return null;
            }
            synchronized (this) {
                while (isAlive()&&mLooper==null)
                {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return mLooper;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* 按钮回调函数 */
        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Log.d(TAG, "Send Message"+ButtonCount);
                ButtonCount++;

                /* 发送消息 */
                Message msg = new Message();
                mHandler.sendMessage(msg);

                /* 发送消息的另一种方法  消息发送后执行下面的函数 */
                mHandler3.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"Get Message from myThread3");

                    }
                });
            }
        });

        /* 创建一个线程 实现Runnable接口， 不具备处理消息功能 循环打印调试信息 */
        myThread = new Thread( new MyRunnable(),"MessageTestThread");
        myThread.start();

        /* 创建一个线程 具备处理消息的功能 */
        myThread2 = new MyThread();
        myThread2.start();
        /* 当线程2 接收到消息后 调用 handleMessage 函数 */
        mHandler = new Handler(myThread2.getLooper(),new Handler.Callback(){
            public boolean handleMessage(Message msg )
            {
                Log.d(TAG,"Get Message" + 1 );
                return false;
            }
        });

        /* 创建一个线程  具备处理消息功能  直接使用HandlerThread */
        myThread3 = new HandlerThread("myThread3");
        myThread3.start();

        mHandler3 = new Handler(myThread3.getLooper());

    }
}
