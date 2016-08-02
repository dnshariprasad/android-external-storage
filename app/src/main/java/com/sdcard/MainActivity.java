package com.sdcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_main_create_directory).setOnClickListener(this);
        findViewById(R.id.main_delete_directory).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_create_directory:
                CreateDirectoryActivity.start(this);
                break;
            case R.id.main_delete_directory:
                break;
        }
    }
}
