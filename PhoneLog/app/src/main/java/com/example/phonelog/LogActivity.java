package com.example.phonelog;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listview;
    Boolean nameIsTrue = true;
    EditText name;
    EditText number;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        name = findViewById(R.id.nameShow);
        number = findViewById(R.id.numberShow);
        name.setEnabled(false); number.setEnabled(false);
        show = findViewById(R.id.showBtn); show.setOnClickListener(this);
        listview = (ListView) findViewById(R.id.lstView);

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
                    nameIsTrue = true;
                }
                break;
            case R.id.numberRadio:
                if (checked){
                    name.setEnabled(false);
                    number.setEnabled(true);
                    nameIsTrue = false;
                }
                break;
        }
    }


    public Cursor getTableCursor(){
        if(nameIsTrue){
            String where = String.format("%s = ?", CallLog.Calls.CACHED_NAME);
            String[] args = {name.getText().toString()};
            Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, where, args, null);
            return cursor;
        }
        else{
            String where = String.format("%s = ?", CallLog.Calls.NUMBER);
            String[] args = {number.getText().toString()};
            Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, where, args, null);
            return cursor;
            }
        }

    @Override
    public void onClick(View view) {
        if(view == show){
            int[] id = { R.id.nameslist, R.id.numberList ,R.id.durationslist, R.id.dateslist };
            String[] table = new String[] {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DURATION, CallLog.Calls.DATE};
            Cursor c = getTableCursor();

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_template, c, table, id, 0);
            adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

                public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {

                    if (aColumnIndex == aCursor.getColumnIndex(CallLog.Calls.DATE)) {
                        Log.d("test", "setViewValue: " );
                        String createDate = aCursor.getString(aColumnIndex);
                        TextView textView = (TextView) aView;
                        SimpleDateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
                        Log.d("test", "setViewValue: " + createDate);
                        Date date = new Date(Long.parseLong(createDate));
                        textView.setText(obj.format(date).toString());
                        return true;
                    }

                    return false;
                }
            });
            listview.setAdapter(adapter);
    }
}


}