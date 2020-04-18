package com.example.classproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InternalStorage extends AppCompatActivity {
    Button read,save;
    EditText filename;
    String myFile="myText";
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);
        read=findViewById(R.id.read);
        save=findViewById(R.id.save);
        filename=findViewById(R.id.filename);
        display=findViewById(R.id.textview);
    }
     public void savefileClick(View view){
        String name=filename.getText().toString();
        if(name.equals(String.valueOf(""))){
            filename.setError("Please Enter File Name");
        }
        else{

            try{
                FileOutputStream fileout=openFileOutput(myFile,MODE_PRIVATE);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                outputWriter.write(name);
                outputWriter.close();
                filename.setText("");
                Toast.makeText(this, "File Saved SuccessFully !", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
     }
     public void readfileClick(View view){
        try{
            FileInputStream filein=openFileInput(myFile);
            InputStreamReader inputread=new InputStreamReader(filein);
            BufferedReader br=new BufferedReader(inputread);
            StringBuilder sb=new StringBuilder();
            String line=null;
            while ((line=br.readLine())!=null){
                sb.append(line);
            }
            filein.close();
            inputread.close();
            Toast.makeText(this, "File Read SuccessFully", Toast.LENGTH_SHORT).show();
            display.setText("File : "+sb.toString());
        }catch (java.io.IOException e){
            e.printStackTrace();

        }
     }

}
