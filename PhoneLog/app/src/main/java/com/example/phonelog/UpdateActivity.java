package com.example.phonelog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.CallLog;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Random;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name; EditText number; EditText duration; Spinner type;
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name = findViewById(R.id.nameUpdate);
        number = findViewById(R.id.numberUpdate);
        duration = findViewById(R.id.durationUpdate);
        type = findViewById(R.id.typeUpdate);

        update = findViewById(R.id.callUpdate); update.setOnClickListener(this);


    }




    @Override
    public void onClick(View view) {
        if(view == update){
            updateFakeCall();
        }
    }

    public void updateFakeCall(){
        ContentValues contentValues = new ContentValues();

        contentValues.put(CallLog.Calls.CACHED_NAME, name.getText().toString());
        contentValues.put(CallLog.Calls.NUMBER, number.getText().toString());
        contentValues.put(CallLog.Calls.DURATION, Integer.parseInt(duration.getText().toString()));
        switch (type.getSelectedItem().toString()){
            case "Incoming":
                contentValues.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
                break;
            case "Outgoing":
                contentValues.put(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE);
                break;
            case "Missed":
                contentValues.put(CallLog.Calls.TYPE, CallLog.Calls.MISSED_TYPE);
                break;
            case "Rejected":
                contentValues.put(CallLog.Calls.TYPE, CallLog.Calls.REJECTED_TYPE);
                break;
        }
        String where = String.format("%s = ?", CallLog.Calls.NUMBER);
        String[] args = {number.getText().toString()};
        ContentResolver contentResolver = getContentResolver();
        contentResolver.update(CallLog.Calls.CONTENT_URI, contentValues, where, args);
    }
}