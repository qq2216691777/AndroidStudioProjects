package com.example.zws.led_001;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.os.IBinder;
//import android.os.ILedService;
//import android.os.ServiceManager;

public class MainActivity extends AppCompatActivity {

    private Button button = null;
    private boolean ledon = false;
    private CheckBox checkbuttonLed1 = null;
    private CheckBox checkbuttonLed2 = null;
    private CheckBox checkbuttonLed3 = null;
    private CheckBox checkbuttonLed4 = null;
    //private ILedService iLedService=null;
    Object proxy = null;
    Method ledCtrl = null;

    class MyButtonListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {

            ledon = !ledon;
            if(ledon) {
                button.setText("ALL OFF");
                checkbuttonLed1.setChecked(true);
                checkbuttonLed2.setChecked(true);
                checkbuttonLed3.setChecked(true);
                checkbuttonLed4.setChecked(true);
                try {
                    for(int i=0; i<4; i++)
                        ledCtrl.invoke(proxy,i,1);
                        //iLedService.ledCtrl(i,1);
                }catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                } catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }

            }
            else
            {
                button.setText("ALL ON");
                checkbuttonLed1.setChecked(false);
                checkbuttonLed2.setChecked(false);
                checkbuttonLed3.setChecked(false);
                checkbuttonLed4.setChecked(false);
                try {
                    for(int i=0; i<4; i++)
                        ledCtrl.invoke(proxy,i,0);
                        //iLedService.ledCtrl(i,0);
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                } catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.BUTTON);
        button.setOnClickListener(new MyButtonListener());
 /*       button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                ledon = !ledon;
                if(ledon)
                    button.setText("ALL OFF");
                else
                    button.setText("ALL ON");
            }
        });*/

        checkbuttonLed1 = (CheckBox) findViewById(R.id.checkboxLED1);
        checkbuttonLed2 = (CheckBox) findViewById(R.id.checkboxLED2);
        checkbuttonLed3 = (CheckBox) findViewById(R.id.checkboxLED3);
        checkbuttonLed4 = (CheckBox) findViewById(R.id.checkboxLED4);

        try {
            //iLedService = ILedService.Stub.asInterface(ServiceManager.getService("led"));
            Method getService = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder ledService = (IBinder) getService.invoke(null,"led");
            Method asInterface = Class.forName("android.os.ILedService$Stub").getMethod("asInterface",IBinder.class);
            proxy = asInterface.invoke(null,ledService);
            ledCtrl = Class.forName("android.os.ILedService$Stub$Proxy").getMethod("ledCtrl",int.class,int.class);
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch ( IllegalAccessException e)
        {
            e.printStackTrace();
        } catch ( InvocationTargetException e)
        {
            e.printStackTrace();
        }
       // ledCtrl.invoke(proxy,0,1);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        try {
            // Check which checkbox was clicked
            switch (view.getId()) {
                case R.id.checkboxLED1:
                    if (checked) {
                        //iLedService.ledCtrl(0, 1);
                        ledCtrl.invoke(proxy,0,1);
                        Toast.makeText(getApplicationContext(), "LED1 ON", Toast.LENGTH_SHORT).show();
                    } else {
                        //iLedService.ledCtrl(0, 0);
                        ledCtrl.invoke(proxy,0,0);
                        Toast.makeText(getApplicationContext(), "LED1 OFF", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.checkboxLED2:
                    if (checked) {
                        //iLedService.ledCtrl(1, 1);
                        ledCtrl.invoke(proxy,1,1);
                        Toast.makeText(getApplicationContext(), "LED2 ON", Toast.LENGTH_SHORT).show();
                    } else {
                        //iLedService.ledCtrl(1, 0);
                        ledCtrl.invoke(proxy,1,0);
                        Toast.makeText(getApplicationContext(), "LED2 OFF", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.checkboxLED3:
                    if (checked) {
                        //iLedService.ledCtrl(2, 1);
                        ledCtrl.invoke(proxy,2,1);
                        Toast.makeText(getApplicationContext(), "LED3 ON", Toast.LENGTH_SHORT).show();
                    } else {
                        //iLedService.ledCtrl(2, 0);
                        ledCtrl.invoke(proxy,2,0);
                        Toast.makeText(getApplicationContext(), "LED3 OFF", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.checkboxLED4:
                    if (checked) {
                        //iLedService.ledCtrl(3, 1);
                        ledCtrl.invoke(proxy,3,1);
                        Toast.makeText(getApplicationContext(), "LED4 ON", Toast.LENGTH_SHORT).show();
                    } else {
                        //iLedService.ledCtrl(3, 0);
                        ledCtrl.invoke(proxy,3,0);
                        Toast.makeText(getApplicationContext(), "LED4 OFF", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default: break;
                // TODO: Veggie sandwich
            }
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}
