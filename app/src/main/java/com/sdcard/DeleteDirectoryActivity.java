package com.sdcard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteDirectoryActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DeleteDirectoryActivity";

    private EditText et_delete_directory_name;
    private Button btn_delete_directory;

    public static void start(Context context) {
        Intent starter = new Intent(context, DeleteDirectoryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_directory);
        //pull views
        et_delete_directory_name = (EditText) findViewById(R.id.et_delete_directory_name);
        btn_delete_directory = (Button) findViewById(R.id.btn_delete_directory);

        //home back enabled
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set listeners
        btn_delete_directory.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btn_delete_directory.performClick();
                } else {
                    Toast.makeText(DeleteDirectoryActivity.this, getResources().getString(R.string.toast_write_permission_required), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete_directory:
                String dirName = et_delete_directory_name.getText().toString().trim();
                if ("".equals(dirName)) {
                    Toast.makeText(DeleteDirectoryActivity.this, getResources().getString(R.string.toast_empty_directory_name), Toast.LENGTH_SHORT).show();
                    return;
                }
                deleteDirectory(dirName);
                break;
        }
    }

}
