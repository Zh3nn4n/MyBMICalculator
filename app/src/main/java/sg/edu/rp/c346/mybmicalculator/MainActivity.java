package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    EditText etweight;
    EditText etheight;
    Button calc;
    Button reset;
    TextView tvbmi;
    TextView tvdate;
    Calendar now;
    TextView tvOutcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         etweight = findViewById(R.id.editTextWeight);
         etheight = findViewById(R.id.editTextHeight);

         calc = findViewById(R.id.buttonCalc);
         reset = findViewById(R.id.buttonReset);
         tvbmi = findViewById(R.id.textViewBMI);
         tvdate = findViewById(R.id.textViewDate);
         tvOutcome = findViewById(R.id.textViewOutcome);


        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Step 1a: Get the user input from the EditTet and store it in a variable


                float Height = Float.parseFloat(etheight.getText().toString());
                float Weight= Float.parseFloat(etweight.getText().toString());
                float bmi=Weight/(Height*Height);

                if (bmi<18.5){
                    tvOutcome.setText("You are underweight");

                }else if(bmi<24.9){
                    tvOutcome.setText("Your BMI is normal");

                }else if(bmi<29.9){
                    tvOutcome.setText("You are overweight");
                }else{
                    tvOutcome.setText("You are obese");
                }
                //Step 1b: Obtain an instance of the SharedPreference
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                //Step 1c: Obtain an instance of the SharedPreference Editor for update later
                SharedPreferences.Editor preEdit = prefs.edit();

                //Step 1d: Add the key-value pair
                Calendar currentDT = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = currentDT.get(Calendar.DAY_OF_MONTH) + "/" +
                        (currentDT.get(Calendar.MONTH)+1) + "/" +
                        currentDT.get(Calendar.YEAR) + " " +
                        currentDT.get(Calendar.HOUR_OF_DAY) + ":" +
                        currentDT.get(Calendar.MINUTE);

                String str1 = String.format("%.3f",bmi);
                tvdate.setText("Last Calculated Date: "+ datetime);
                System.out.println(str1);
                tvbmi.setText("Last Calculated BMI: "+str1);

                preEdit.putFloat("Height", Height);
                preEdit.putFloat("Weight", Weight);
                preEdit.putFloat("Bmi",bmi);
                preEdit.putString("date",datetime);
                //Step 1e: Call commit() to save the changes into SharedPreference
                preEdit.commit();



            }
        });

         reset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                 //Step 1c: Obtain an instance of the SharedPreference Editor for update later

                 etweight.setText("");
                 etheight.setText("");
                 tvdate.setText("Last Calculated Date: ");
                 tvbmi.setText("Last Calculated BMI: ");
             }
         });

    }

    @Override
    protected void onPause() {
        super.onPause();

        //Step 1a: Get the user input from the EditTet and store it in a variable
        float Height = Float.parseFloat(etheight.getText().toString());
        float  Weight= Float.parseFloat(etweight.getText().toString());
        float bmi=Weight/(Height*Height);
        //Step 1b: Obtain an instance of the SharedPreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor preEdit = prefs.edit();

        //Step 1d: Add the key-value pair
        preEdit.putFloat("Height", Height);
        preEdit.putFloat("Weight", Weight);


        preEdit.putFloat("Bmi",bmi);
        //Step 1e: Call commit() to save the changes into SharedPreference
        preEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SHaredPreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data with the key "greeting" from the SharedPreference object
        float bmi= prefs.getFloat("Bmi",0);
        String datetime = prefs.getString("date","");
        //Step 2c: Update the UI element with the value
        //Create a Calendar object with current date and time


        String bmiresult=String.format("%.3f",bmi);

        tvdate.setText("Last Calculated Date: "+ datetime);

        tvbmi.setText("Last Calculated BMI: "+bmiresult);
    }




}
