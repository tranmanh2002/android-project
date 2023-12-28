package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.database.databasedoctruyen;

public class SignIn extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnDangKy;

    //Tao doi tuong cho databasedoctruyen
    databasedoctruyen databasedoctruyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        AnhXa();
        // Doi tuong database doc truyen
        databasedoctruyen = new databasedoctruyen(this);

        //Tao su kien click button chuyen sang man hinh dang ki voi Intent
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, ManDangKy.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                //Su dung con tro de lay du lieu
                Cursor cursor = databasedoctruyen.getDaTa();

                //Thuc hien vong lap de lay du lieu tu cursor voi movetotext() di chuyen tiep
                while (cursor.moveToNext()){
                    //lay du lieu va gan vao bien, du lieu tai khoan o o 1 va mat khau o o 2, ô 0 là id tai khoan, o 3 là email, 4 là phân quyền
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    if(datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)){
                        //Lay du lieu phanquyen va id
                        int phanquyen = cursor.getInt(4);
                        int idd = cursor.getInt(0);
                        String email = cursor.getString(3);
                        String tentk = cursor.getString(1);
                        //chuyen qua man hinh main
                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                        //gui du lieu qua activity la mainactivity
                        intent.putExtra("phanq", phanquyen);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentk);

                        startActivity(intent);

                    }
                }
                //thuc hien tra cursor ve dau
                cursor.moveToFirst();
                cursor.close();
            }
        });
        //Run.....

    }



    private void AnhXa() {
        edtMatKhau = findViewById(R.id.matkhau);
        edtTaiKhoan = findViewById(R.id.taikhoan);
        btnDangKy = findViewById(R.id.dangky);
        btnDangNhap = findViewById(R.id.dangnhap);


    }
}