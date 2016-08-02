package com.sdcard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateDirectoryActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CreateDirectoryActivity";
    private EditText et_directory_name;
    private Button btn_create_directory;

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateDirectoryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_directory);

        //pull views
        et_directory_name = (EditText) findViewById(R.id.et_directory_name);
        btn_create_directory = (Button) findViewById(R.id.btn_create_directory);

        //home back enabled
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set listeners
        btn_create_directory.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btn_create_directory.performClick();
                } else {
                    Toast.makeText(CreateDirectoryActivity.this, getResources().getString(R.string.toast_write_permission_required), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_directory:
                String dirName = et_directory_name.getText().toString().trim();
                if ("".equals(dirName)) {
                    Toast.makeText(CreateDirectoryActivity.this, getResources().getString(R.string.toast_empty_directory_name), Toast.LENGTH_SHORT).show();
                    return;
                }
                createDirectory(dirName);
                break;
        }
    }
}
