package com.example.binarycoders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Button buttonPage1 = findViewById(R.id.buttonPage1);
        Button buttonPage2 = findViewById(R.id.buttonPage2);
        Button buttonPage3 = findViewById(R.id.buttonPage3);

        buttonPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launcher.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launcher.this, Doc_prec.class);
                startActivity(intent);
            }
        });


        buttonPage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launcher.this, bug_bite.class);
                startActivity(intent);
            }
        });
    }
}