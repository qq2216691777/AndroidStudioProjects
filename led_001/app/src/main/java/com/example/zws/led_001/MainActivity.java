package com.example.zws.led_001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.zws.hardlibrary.*;

public class MainActivity extends AppCompatActivity {

    private Button button = null;
    private boolean ledon = false;
    private CheckBox checkbuttonLed1 = null;
    private CheckBox checkbuttonLed2 = null;
    private CheckBox checkbuttonLed3 = null;
    private CheckBox checkbuttonLed4 = null;


    class MyButtonListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            HardControl hardControl = new HardControl();

            ledon = !ledon;
            if(ledon) {
                button.setText("ALL OFF");
                checkbuttonLed1.setChecked(true);
                checkbuttonLed2.setChecked(true);
                checkbuttonLed3.setChecked(true);
                checkbuttonLed4.setChecked(true);
                for(int i=0; i<4; i++)
                    HardControl.ledCtrl(i,1);
            }
            else
            {
                button.setText("ALL ON");
                checkbuttonLed1.setChecked(false);
                checkbuttonLed2.setChecked(false);
                checkbuttonLed3.setChecked(false);
                checkbuttonLed4.setChecked(false);
                for(int i=0; i<4; i++)
                    HardControl.ledCtrl(i,0);
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


        HardControl.ledOpen();
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxLED1:
                if (checked)
                {
                    HardControl.ledCtrl(0,1);
                    Toast.makeText(getApplicationContext(),"LED1 ON",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    HardControl.ledCtrl(0,0);
                    Toast.makeText(getApplicationContext(),"LED1 OFF",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.checkboxLED2:
                if (checked)
                {
                    HardControl.ledCtrl(1,1);
                    Toast.makeText(getApplicationContext(),"LED2 ON",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    HardControl.ledCtrl(1,0);
                    Toast.makeText(getApplicationContext(),"LED2 OFF",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.checkboxLED3:
                if (checked)
                {
                    HardControl.ledCtrl(2,1);
                    Toast.makeText(getApplicationContext(),"LED3 ON",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    HardControl.ledCtrl(2,0);
                    Toast.makeText(getApplicationContext(),"LED3 OFF",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.checkboxLED4:
                if (checked)
                {
                    HardControl.ledCtrl(3,1);
                    Toast.makeText(getApplicationContext(),"LED4 ON",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    HardControl.ledCtrl(3,0);
                    Toast.makeText(getApplicationContext(),"LED4 OFF",Toast.LENGTH_SHORT).show();
                }
                break;
            // TODO: Veggie sandwich
        }
    }
}
