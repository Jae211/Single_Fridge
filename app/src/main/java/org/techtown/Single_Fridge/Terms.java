package org.techtown.Single_Fridge;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Terms extends AppCompatActivity {
    String input;
    TextView textRead;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        input = getIntent().getStringExtra("info");

        textRead = findViewById(R.id.textView_term);
        textRead.setText(readText());
    }

    private String readText() {
        String data = null;

        InputStream inputStream = null;
        if(input.equals("ToS")){
            inputStream = getResources().openRawResource(R.raw.terms_of_service);
        } else if (input.equals("PP")){
            inputStream = getResources().openRawResource(R.raw.privacy_policy);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();

            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            //data = new String(byteArrayOutputStream.toByteArray(),"MS949");
            data = new String(byteArrayOutputStream.toByteArray());
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
