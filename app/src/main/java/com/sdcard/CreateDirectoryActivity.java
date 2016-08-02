package com.sdcard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class CreateDirectoryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CreateDirectoryActivity";

    public static final int REQUEST_PERMISSION_READ_STORAGE = 1;
    public static final int REQUEST_PERMISSION_WRITE_STORAGE = 2;

    public static String[] PERMISSIONS_READ_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] PERMISSIONS_WRITE_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    private boolean createDirectory(String dirName) {
        boolean isCreated = false;
        int writeStorePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeStorePermission != PackageManager.PERMISSION_GRANTED) {
            //Requesting for permission
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_WRITE_STORAGE,
                    REQUEST_PERMISSION_WRITE_STORAGE);
        } else {
            //create file(file or dir) object
            File file = new File(Environment.getExternalStorageDirectory() + "/" + dirName + "/");
            if (!file.exists()) {//check is already exists
                isCreated = file.mkdir();//create is not exist
                Toast.makeText(CreateDirectoryActivity.this, getResources().getString(R.string.toast_directory_created), Toast.LENGTH_SHORT).show();
            } else
                Log.d(TAG, "createDirectory: Already created.");
        }
        return isCreated;
    }
}
