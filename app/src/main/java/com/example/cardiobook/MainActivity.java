/**
 * @version: V1.0
 * @author: Yifei Bai
 * @className: MainActivity
 * @packageName:com.example.cardiobook
 * @description: this class is used when user opens this app.
 * @data: 2019-02-4
 * @Designreason:
 **/

package com.example.cardiobook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> temp;
    ArrayList<Measurement> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load measurements from previous and show them on the screen
        listView = findViewById(R.id.listview);
        temp = new ArrayList<>();
        loadFromFile();
        temp.addAll(transfer());
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, temp);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        DataController.setListener(listener);
    }

    //when user clicks long time, let user to choose delete or cancel.
    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure to delete this measurement?");
            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataController.removeMesurement(position);
                    saveInFile();
                }
            });

            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
            return true;
        }
    };
    //when user clicks "ADD" button, call addActivity and give the position
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("data", position);
            startActivity(intent);
        }
    };


    // Using listener to update array of measurements
    private Listener listener = new Listener() {
        @Override
        public void update() {
            temp.clear();
            temp.addAll(transfer());
            saveInFile();
            arrayAdapter.notifyDataSetChanged();
        }
    };


    //when user clicks "ADD" button, call addActivity
    public void addButton(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);

    }

    // Transfer the measurements from DataController to a list to show on the screen
    private ArrayList<String> transfer(){
        ArrayList<String> result = new ArrayList<>();
        int i = 1;
        for(Measurement measurement : DataController.getDataList()){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String dateString = simpleDateFormat.format(measurement.getDate());


            if(measurement.getSp()<90 ||measurement.getSp()>140) {
                if (measurement.getDp() < 60 || measurement.getDp() > 90) {
                    result.add("Measurement #" + i + "  created on " + dateString + "\n\n" + "Systolic pressure:"
                            + measurement.getSp() + " mm Hg;  *abnormal" + "\n" + "Diastolic pressure: " + measurement.getDp() + " mm Hg;  *abnormal"
                            + "\nHeart rate: " + measurement.getHr() + " per minute.");
                    i++;
                    continue;
                }
            }

            if (measurement.getSp()<90 ||measurement.getSp()>140){
                result.add("Measurement #" + i +"  created on " + dateString + "\n\n" + "Systolic pressure:"
                        + measurement.getSp() + " mm Hg;  *abnormal" + "\n" + "Diastolic pressure: " + measurement.getDp()+ " mm Hg;"
                        + "\nHeart rate: "+ measurement.getHr() + " per minute.");
                i++;
                continue;
            }

            if (measurement.getDp()<60 ||measurement.getDp()>90){
                result.add("Measurement #" + i +"  created on " + dateString + "\n\n" + "Systolic pressure:"
                        + measurement.getSp() + " mm Hg; " + "\n" + "Diastolic pressure: " + measurement.getDp()+ " mm Hg;  *abnormal"
                        + "\nHeart rate: "+ measurement.getHr() + " per minute.");
                i++;
                continue;
            }



            result.add("Measurement #" + i +"  created on " + dateString + "\n\n" + "Systolic pressure:"
                    + measurement.getSp() + " mm Hg;" + "\n" + "Diastolic pressure: " + measurement.getDp()+ " mm Hg;"
                    + "\nHeart rate: "+ measurement.getHr() + " per minute.");

            i++;

        }
        return result;
    }



/***************************************************************************************
*    loadFromFile() and saveInFile() are from
 *   Title: <LonelyTwitter>
*    Author: <University of Alberta, Department-Computing Science>
*    Date: <2019>
*    Code version: <V1>
 ****************************************************************************************/

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Measurement>>(){}.getType();
            tempList = gson.fromJson(in, listType);
            DataController.setDataList(tempList);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        }
    }

    public void saveInFile() {
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
