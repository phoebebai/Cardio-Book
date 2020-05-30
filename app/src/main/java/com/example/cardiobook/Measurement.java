/**
 * @version: V1.0
 * @author: Yifei Bai
 * @className: Measurement
 * @packageName:com.example.cardiobook
 * @description: this class is a measurement object.
 * @designreason: each measurement includes date, comment and so on
 * @data: 2019-02-4
 **/

package com.example.cardiobook;

import java.io.Serializable;
import java.util.Date;

public class Measurement{

    private Date date;
    private int sp;
    private int dp;
    private int hr;
    private String comment;

    public Measurement(int sp_value, int dp_value,int hr_value, Date selectedDate, String commentString ){

        this.date = selectedDate;
        this.sp = sp_value;
        this.dp = dp_value;
        this.hr = hr_value;
        this.comment = commentString;

    }


    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }


    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }


    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
