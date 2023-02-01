package com.example.phonelog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name; EditText number; EditText duration; EditText date; Spinner type;
    Button add;
    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.date);
        number = findViewById(R.id.number);
        duration = findViewById(R.id.duration);

        date = findViewById(R.id.date);
        date.setFocusable(false); date.setClickable(true);

        dateChoice = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddActivity.this,dateChoice,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        type = findViewById(R.id.type);
        add = findViewById(R.id.addDate);

        add.setOnClickListener(this);


    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        date.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        if(view == add){
            addCall();
        }
    }


    private void addCall(){
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
        contentValues.put(CallLog.Calls.DATE, myCalendar.getTimeInMillis());
        Log.d("yoav", "writeFakeCall: writing");
        ContentResolver contentResolver = getContentResolver();
        contentResolver.insert(CallLog.Calls.CONTENT_URI, contentValues);
        Log.d("yoav", "writeFakeCall: written");
    }
}