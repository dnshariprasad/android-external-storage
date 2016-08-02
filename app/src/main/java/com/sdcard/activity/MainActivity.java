package com.sdcard.activity;

import android.os.Bundle;
import android.view.View;

import com.sdcard.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_sdcard).setOnClickListener(this);
        findViewById(R.id.btn_main_create_directory).setOnClickListener(this);
        findViewById(R.id.main_delete_directory).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sdcard:
                SdCardActivity.start(this);
                break;
            case R.id.btn_main_create_directory:
                CreateDirectoryActivity.start(this);
                break;
            case R.id.main_delete_directory:
                DeleteDirectoryActivity.start(this);
                break;
        }
    }
}
