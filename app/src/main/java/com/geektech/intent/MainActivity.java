package com.geektech.intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3, btnCall;
    private TextView etValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    private void initViews() {
        etValue = findViewById(R.id.et_value);
        btn1 = findViewById(R.id.btn_open_next_screen);
        btn2 = findViewById(R.id.btn_whatsapp);
        btn3 = findViewById(R.id.btn_search);
        btnCall = findViewById(R.id.btn_call);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etValue.getText().toString();
                String url = "https://api.whatsapp.com/send?phone=" + phone;
                Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW);
                whatsAppIntent.setData(Uri.parse(url));
                startActivity(whatsAppIntent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, etValue.getText().toString());
                startActivity(searchIntent);
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String phone = etValue.getText().toString();
//             if (phone.isEmpty()){
//                 Toast.makeText(getApplicationContext(),"PLEASE ENTER NUMBER!",
//                         Toast.LENGTH_SHORT).show();
//             }else {
//                 String s ="tel"+phone;
//                 Intent intent = new Intent(Intent.ACTION_CALL);
//                 intent.setData(Uri.parse(s));
//                 startActivity(intent);
//             }
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    getPhoneTop();
                }
            }
        });
    }

   private void getPhoneTop() {
       String phone = etValue.getText().toString();
       Intent myIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel",phone,null));
       startActivity(myIntent);
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                getPhoneTop();
            }
        }
    }
}