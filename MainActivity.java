package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.CircularArray;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.myapplication.adapter.adapterTruyen;
import com.example.myapplication.adapter.adapterchuyenmuc;
import com.example.myapplication.adapter.adapterthongtin;
import com.example.myapplication.database.databasedoctruyen;
import com.example.myapplication.model.TaiKhoan;
import com.example.myapplication.model.Truyen;
import com.example.myapplication.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView, listViewNew, listViewThongTin;
    DrawerLayout drawerLayout;

    String email, tentaikhoan;

    ArrayList<Truyen> TruyenArrayList;

    adapterTruyen adapterTruyen;

    ArrayList<chuyenmuc> chuyenmucArrayList;

    ArrayList<TaiKhoan> taiKhoanArrayList;

    databasedoctruyen databasedoctruyen;

    adapterchuyenmuc adapterchuyenmuc;
    adapterthongtin adapterthongtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasedoctruyen = new databasedoctruyen(this);
        //nhận dữ liệu ở màn đăng nhập gửi
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq", 0);
        int idd = intentpq.getIntExtra("idd", 0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        AnhXa();
        ActionBar();
        ActionViewFlipper();

        //bat su kien click item
        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ManNoiDung.class);

                String tent = TruyenArrayList.get(position).getTenTruyen();
                String noidungt = TruyenArrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);
                startActivity(intent);
            }
        });
    }
    //thanh acctionbar voi toolbar
    private  void ActionBar(){
        //hàm hỗ trợ toolbar
        setSupportActionBar(toolbar);
        //set nut cho actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Creat icon for toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        //bat click item cho listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int i;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Đăng bài
                if (position == 0){
                    if(i == 2){
                        Intent intent = new Intent(MainActivity.this, ManAdmin.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Bạn không có quyền đăng bài", Toast.LENGTH_SHORT).show();
                        Log.e("Đăng bài: ", "Bạn không có quyền");
                    }
                }
                //Neu vi tri an vao la thong tin thi se chuyen qua man thong tin app
                else if (position == 1){
                    Intent intent = new Intent(MainActivity.this, ManThongTin.class);
                    startActivity(intent);

                }
                else if (position == 2){
                    finish();
                }

            }
        });

    }
    //Phuong thuc cho chay quang cao voi viewfilliper
    private void ActionViewFlipper(){
        //Mang chu tam anh cho quang cao
        ArrayList<String> mangquangcao = new ArrayList<>();
        //Add anh vao mang
        mangquangcao.add("https://cdn.hellobacsi.com/wp-content/uploads/2017/11/14-cau-chuyen-y-nghia-ban-ke-cho-be-nghe-moi-dem-4-e1511109098651.jpg");
        mangquangcao.add("https://cdn.hellobacsi.com/wp-content/uploads/2017/12/ke-chuyen-be-nghe-de-va-cao.jpg");
        mangquangcao.add("https://cdn.hellobacsi.com/wp-content/uploads/2017/11/14-cau-chuyen-y-nghia-ban-ke-cho-be-nghe-moi-dem-1.jpg");
        mangquangcao.add("https://cdn.hellobacsi.com/wp-content/uploads/2017/11/14-cau-chuyen-y-nghia-ban-ke-cho-be-nghe-moi-dem-2-e1511109071378.jpg");

        //Dung vong lap for gan anh vao imageView, roi tu imgview len app
        for (int i = 0; i <mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            //su dung ham thu vien picasso
            CircularArray<Object> Picasso;
            //su dung ham thu vien picasso
            com.squareup.picasso.Picasso.get().load(mangquangcao.get(i)).into(imageView);
            //phuong thuc chinh tam hinh vua voi khung quang cao
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //Them anh co imageview vao viewflipper
            viewFlipper.addView(imageView);
        }
        //thiet lap tu dong chay trong 4 giay
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        //goi animation cho vao va ra
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_night);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);

        //Goi animation vao trong viewflipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);

    }

    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        listViewNew = findViewById(R.id.listViewNew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationView);
        listViewThongTin = findViewById(R.id.listViewThongTin);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArrayList = new ArrayList<>();

        Cursor cursor1 = databasedoctruyen.getData1();
        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArrayList.add( new Truyen(id, tentruyen, noidung, anh, id_tk));

            adapterTruyen = new adapterTruyen(getApplicationContext(), TruyenArrayList);
            listViewNew.setAdapter(adapterTruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();

        //Thong tin
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan, email));

        adapterthongtin = new adapterthongtin(this, R.layout.navigation_thongtin, taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin);

        //chuyen muc
        chuyenmucArrayList = new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Đăng bài", R.drawable.ic_post));
        chuyenmucArrayList.add(new chuyenmuc("Thông tin", R.drawable.ic_baseline_face_24));
        chuyenmucArrayList.add(new chuyenmuc("Đăng xuất", R.drawable.ic_baseline_login_24));

        adapterchuyenmuc = new adapterchuyenmuc(this,R.layout.chuyenmuc, chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);
    }
    //Nạp một menu tìm kiếm vào actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //neu click vao icon tim kiem se chuyen den man hinh tim kiem
        switch (item.getItemId()){
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this, ManTimKiem.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}