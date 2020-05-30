/**
 * @version: V1.0
 * @author: Yifei Bai
 * @className: DataController
 * @packageName:com.example.cardiobook
 * @description: this class is used to store a list of measurements.
 * @data: 2019-02-4
 * @designreason: store all the measurements.
 **/


package com.example.cardiobook;


import java.util.ArrayList;

public class DataController {
    private static ArrayList<Measurement> dataList = new ArrayList<>();
    private static Listener listener = null;

    public static void setDataList(ArrayList<Measurement> dataList){
        DataController.dataList = dataList;
    }

    public static ArrayList<Measurement> getDataList() {
        return dataList;
    }

    public static void addMeasurement(Measurement measurement){
        dataList.add(measurement);
        notifyListener();
    }

    public static void removeMesurement(int index){
        dataList.remove(index);
        notifyListener();
    }

    public static void setListener(Listener listener){
        DataController.listener = listener;
    }

    public static void notifyListener(){
        if(listener!=null)
            listener.update();
    }
}
