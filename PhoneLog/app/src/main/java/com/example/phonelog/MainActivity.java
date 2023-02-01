package com.example.phonelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button show; Button add; Button update; Button remove;
    String[] PERMISSIONS = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.show); show.setOnClickListener(this);
        add = findViewById(R.id.add); add.setOnClickListener(this);
        update = findViewById(R.id.update); update.setOnClickListener(this);
        remove = findViewById(R.id.remove); remove.setOnClickListener(this);


        int PERMISSION_ALL = 1;
        while (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    @Override
    public void onClick(View view) {
        if(view == show){
            Intent intent = new Intent(MainActivity.this, LogActivity.class);
            startActivity(intent);
        }
        else if(view == add){
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        }
        else if(view == update){
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            startActivity(intent);
        }
        else if(view == remove){
            Intent intent = new Intent(MainActivity.this, deleteActivity.class);
            startActivity(intent);
        }
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}