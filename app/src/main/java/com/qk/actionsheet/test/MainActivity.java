package com.qk.actionsheet.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qk.actionsheet.DialogActionSheet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go(View view) {
        new DialogActionSheet(MainActivity.this).builder().setTitle("标题")
                .setTitleColor(Color.parseColor("#36ae9e"))
                .setCancelText("关闭").setCancelColor(Color.parseColor("#FD4A2E"))
                .addSheetItem("拍摄一张", Color.parseColor("#555555"),
                        new DialogActionSheet.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                            }
                        })
                .addSheetItem("从相册中选择", Color.parseColor("#555555"),
                        new DialogActionSheet.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                            }
                        }).show();
    }
}
