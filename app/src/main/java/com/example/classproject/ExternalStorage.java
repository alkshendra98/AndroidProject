package com.example.classproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class ExternalStorage extends AppCompatActivity {
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE=123;
    Button save,read;
    EditText edt;
    TextView display;
    String filename="myfile.txt";
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);
        read=findViewById(R.id.read);
        save=findViewById(R.id.save);
        edt=findViewById(R.id.filename);
        display=findViewById(R.id.textview);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pc= ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(pc== PackageManager.PERMISSION_GRANTED){
                    saveFile();
                } else{
                    ActivityCompat.requestPermissions(ExternalStorage.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXTERNAL_STORAGE);
                }

            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
            }
        });
         }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_WRITE_EXTERNAL_STORAGE:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    saveFile();
                } else{
                    Toast.makeText(this, "Please Grant Permission to Save File", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
    private void readFile(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            FileInputStream fin = new FileInputStream(file);
            InputStreamReader is = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(is);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line=br.readLine())!=null){
                sb.append(line);
            }
            fin.close();
            is.close();
            display.setText("File : "+sb.toString());
            Toast.makeText(this, "Data Retrived : "+sb.toString(), Toast.LENGTH_SHORT).show();
        }   catch (java.io.IOException e){
            e.printStackTrace();
        }
    }
    private void saveFile() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            FileOutputStream fos = new FileOutputStream(file);
            name = edt.getText().toString();
            fos.write(name.getBytes());
            fos.close();
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
