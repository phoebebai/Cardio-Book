/**
 * @version: V1.0
 * @author: Yifei Bai
 * @className:EditActivity
 * @packageName:com.example.cardiobook
 * @description: this class is used when user wants to edit an existing measurement.
 * @data: 2019-02-4
 * @designreason: there is requried activity to let user edit
 **/

package com.example.cardiobook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    EditText date;
    EditText time;
    EditText sp;
    EditText dp;
    EditText comment;
    EditText hr;
    Measurement measurement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        int position = intent.getIntExtra("data", -1);
        measurement = DataController.getDataList().get(position);

        date = findViewById(R.id.date_viewText);
        time = findViewById(R.id.time_viewText);
        sp = findViewById(R.id.systolicp_viewText);
        dp = findViewById(R.id.diastolicp_viewText);
        comment = findViewById(R.id.comment_viewText);
        hr = findViewById(R.id.heartrate_viewText);

        Date savedDate = measurement.getDate();
        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(savedDate);
        date.setText(dateString.split(" ")[0]);
        time.setText(dateString.split(" ")[1]);
        sp.setText(String.valueOf(measurement.getSp()));
        dp.setText(String.valueOf(measurement.getDp()));
        comment.setText(measurement.getComment());
        hr.setText(String.valueOf(measurement.getHr()));
    }

    public void editBtn(View view){

        String dateString = date.getText().toString();
        String newtime = time.getText().toString();
        int newsp = Integer.parseInt(sp.getText().toString());
        int newdp = Integer.parseInt(dp.getText().toString());
        int newhr = Integer.parseInt(hr.getText().toString());
        String newcomment = comment.getText().toString();

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date selectedDate = null;
        try {
            selectedDate = simpleDateFormat.parse(dateString + " " + newtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (selectedDate == null) {
            Toast.makeText(EditActivity.this, "Your selected date is wrong!", Toast.LENGTH_SHORT).show();
            return;
        } else if(currentDate.before(selectedDate)) {
            Toast.makeText(EditActivity.this, "Your selected date is invalid!", Toast.LENGTH_SHORT).show();
            return;
        }

        measurement.setComment(newcomment);
        measurement.setDate(selectedDate);
        measurement.setDp(newdp);
        measurement.setSp(newsp);
        measurement.setHr(newhr);

        DataController.notifyListener();
        finish();
    }
}
