package com.sdcard.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sdcard.R;
import com.sdcard.adapter.FilesAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SdCardActivity extends BaseActivity {
    private static final String TAG = "SdCardActivity";
    private List<File> filesList;
    private RecyclerView rv_files_list;
    private FilesAdapter filesAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, SdCardActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sd_card);
        rv_files_list = (RecyclerView) findViewById(R.id.rv_files_list);
        //home back enabled
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        filesList = new ArrayList<>();
        checkReadPermissionAndRead();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkReadPermissionAndRead();
                } else {
                    Toast.makeText(SdCardActivity.this, getResources().getString(R.string.toast_write_permission_required), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    public void checkReadPermissionAndRead() {
        int readStorePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readStorePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_READ_STORAGE,
                    REQUEST_PERMISSION_READ_STORAGE);
        } else {
            File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            filesList = new ArrayList<>(Arrays.asList(root.listFiles()));
            buildList();
        }
    }

    private void buildList() {
        if (null == rv_files_list.getAdapter()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_files_list.setLayoutManager(linearLayoutManager);
            filesAdapter = new FilesAdapter(this, filesList);
            rv_files_list.setAdapter(filesAdapter);
        } else {
            filesAdapter.notify(filesList);
        }
    }
}
