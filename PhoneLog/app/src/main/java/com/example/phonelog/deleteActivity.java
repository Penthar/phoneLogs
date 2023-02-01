package com.example.phonelog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class deleteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText number;
    boolean idOfEditText = false;
    Button deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        deleteBtn = findViewById(R.id.deleteBtn); deleteBtn.setOnClickListener(this);
        name = findViewById(R.id.nameDelete);
        number = findViewById(R.id.numberDelete);

        name.setEnabled(false);
        number.setEnabled(false);

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();


        switch(view.getId()) {
            case R.id.nameRadio:
                if (checked){
                    name.setEnabled(true);
                    number.setEnabled(false);
                    idOfEditText = true;
                }
                    break;
            case R.id.numberRadio:
                if (checked){
                    name.setEnabled(false);
                    number.setEnabled(true);
                    idOfEditText = false;
                }
                    break;
        }
    }

    @Override
    public void onClick(View view) {
        if(view == deleteBtn){
            if(idOfEditText){
                deleteFakeCallByName();
            }
            else{
                deleteFakeCallByNumber();
            }
        }
    }

    public void deleteFakeCallByNumber(){
        Log.d("yoav2", "deleteFakeCallByNumber: deleting");
        String where = String.format("%s = ?", CallLog.Calls.NUMBER);
        String[] args = {number.getText().toString()};
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(CallLog.Calls.CONTENT_URI, where, args);
        Log.d("yoav2", "deleteFakeCallByNumber: deletd");
    }

    public void deleteFakeCallByName(){
        Log.d("yoav2", "deleteFakeCallByNumber: deleting");
        String where = String.format("%s = ?", CallLog.Calls.CACHED_NAME);
        Log.d("yoavler", "deleteFakeCallByName: " + name.getText().toString());
        String[] args = {name.getText().toString()};
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(CallLog.Calls.CONTENT_URI, where, args);
        Log.d("yoav2", "deleteFakeCallByNumber: deletd");
    }
}