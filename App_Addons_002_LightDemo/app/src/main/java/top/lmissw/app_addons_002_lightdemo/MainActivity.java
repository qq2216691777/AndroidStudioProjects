package top.lmissw.app_addons_002_lightdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private SeekBar mBackLightSeekBar = null;

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

        /* 关闭自动调光功能 */
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

        mBackLightSeekBar = (SeekBar)findViewById(R.id.seekBar);

        try {
            int brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            mBackLightSeekBar.setProgress(brightness*100/255);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


        mBackLightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int brightness = mBackLightSeekBar.getProgress();
                brightness = brightness*255/100;

                android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,brightness);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

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
