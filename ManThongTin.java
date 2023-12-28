package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ManThongTin extends AppCompatActivity {

    TextView txtThongTinapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_thong_tin);

        txtThongTinapp = findViewById(R.id.textviewthongtin);

        String thongtin = "Ứng dụng được phát hành bởi 'Manh chui'\n" +
                " Uy tín";

        txtThongTinapp.setText(thongtin);

    }
}