package top.lmissw.app_addons_002_lightdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mLightButton=null;
    boolean flashing = false;
    final  private int LED_NOTIFICATION_ID = 123;
    private  LightRunnable mLightRunnable = new LightRunnable();

    private Handler mLightHander = new Handler();
    class LightRunnable implements Runnable {
        @Override
        public void run() {
            if(flashing)
                FlashingLight();
            else
                ClearLED();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLightButton = (Button)findViewById(R.id.button);
        mLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashing = !flashing;

                if (flashing) {
                    mLightButton.setText("Stop Flashing the Light");
                } else {
                    mLightButton.setText("Flashing Light at 20S");
                }

                mLightHander.postDelayed(mLightRunnable,20000);//延时20000ms（20S） 开发板需要设置在15s后息屏

            }
        });
    }

    private void FlashingLight()
    {
        //获得 NOTIFICATION_SERVICE 服务
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //构造 Notification 并设置参数
        Notification notif = new Notification();
        notif.ledARGB = 0xffff00ff;
        notif.flags = Notification.FLAG_SHOW_LIGHTS;
        notif.ledOnMS = 100;
        notif.ledOffMS = 100;
        // 发送设置好的参数
        nm.notify(LED_NOTIFICATION_ID,notif);
    }

    private void ClearLED()
    {
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(LED_NOTIFICATION_ID);
    }
}
