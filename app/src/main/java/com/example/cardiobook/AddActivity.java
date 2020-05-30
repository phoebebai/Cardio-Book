/**
 * @version: V1.0
 * @author: Yifei Bai
 * @className: AddActivity
 * @packageName:com.example.cardiobook
 * @description: this class is used when user wants to add a new measurement.
 * @data: 2019-02-4
 * @Designreason: let user add a measurement
**/

package com.example.cardiobook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private Button create_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        create_btn = findViewById(R.id.edit_btn);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText dateEditText = findViewById(R.id.date_viewText);
                EditText timeEditText = findViewById(R.id.time_viewText);
                EditText spEditText = findViewById(R.id.systolicp_viewText);
                EditText dpEditText = findViewById(R.id.diastolicp_viewText);
                EditText hrEditText = findViewById(R.id.heartrate_viewText);
                EditText comment = findViewById(R.id.comment_editText);

                String dateString = dateEditText.getText().toString();
                String timeString = timeEditText.getText().toString();
                String commentString = comment.getText().toString();

                if (spEditText.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this, "Systolic pressure is wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dpEditText.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this, "Diastolic pressure is wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (hrEditText.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this, "Heart rate is wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }


                int sp_value = Integer.parseInt(spEditText.getText().toString());
                int dp_value = Integer.parseInt(dpEditText.getText().toString());
                int hr_value = Integer.parseInt(hrEditText.getText().toString());



                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                // check the date which is entered by user is beyond now or not.
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Date selectedDate = null;
                try {
                    selectedDate = simpleDateFormat.parse(dateString + " " + timeString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (selectedDate == null) {
                    Toast.makeText(AddActivity.this, "Your selected date is wrong!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(currentDate.before(selectedDate)) {
                    Toast.makeText(AddActivity.this, "Your selected date is invalid!", Toast.LENGTH_SHORT).show();
                    return;
                }


                Measurement measurement = new Measurement(sp_value,dp_value,hr_value, selectedDate , commentString);
                DataController.addMeasurement(measurement);
                Toast.makeText(AddActivity.this, "You created a measurement successfully!", Toast.LENGTH_SHORT).show();
                saveInFile();

                AddActivity.this.finish();
            }
        });
    }



    /***************************************************************************************
     *   saveInFile() is from
     *   Title: <LonelyTwitter>
     *    Author: <University of Alberta, Department-Computing Science>
     *    Date: <2019>
     *    Code version: <V1>
     ****************************************************************************************/

    private void saveInFile() {
        String FILENAME= "file.sav";
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(DataController.getDataList(), writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}