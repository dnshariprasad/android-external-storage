package com.sdcard.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.sdcard.R;
import com.sdcard.adapter.FilesAdapter;
import com.sdcard.manager.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity {
    private static final String TAG = "SdCardActivity";
    private RecyclerView rv_files_list;
    private FilesAdapter filesAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_files_list = (RecyclerView) findViewById(R.id.rv_files_list);

        FileManager.getInstance().pushToBackTrack(Environment.getExternalStorageDirectory().getAbsolutePath());
        checkReadPermissionAndRead(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    @Override
    public void onBackPressed() {
        FileManager.getInstance().popBackTrack();
        if ("-1".equals(FileManager.getInstance().topOfBackTrack())) {
            super.onBackPressed();
            return;
        }
        checkReadPermissionAndRead(FileManager.getInstance().topOfBackTrack());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkReadPermissionAndRead(Environment.getExternalStorageDirectory().getAbsolutePath());
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_write_permission_required), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    public void checkReadPermissionAndRead(String path) {
        int readStorePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readStorePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_READ_STORAGE,
                    REQUEST_PERMISSION_READ_STORAGE);
        } else {
            FileManager.getInstance().getFiles().clear();
            File target = new File(path);
            if (null != target.listFiles())
                FileManager.getInstance().setFiles(new ArrayList<>(Arrays.asList(target.listFiles())));
            buildList();
        }
    }

    private void buildList() {
        if (null == rv_files_list.getAdapter()) {
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            // Attach the layout manager to the recycler view
            rv_files_list.setLayoutManager(gridLayoutManager);
            filesAdapter = new FilesAdapter(this, FileManager.getInstance().getFiles());
            rv_files_list.setAdapter(filesAdapter);
        } else {
            filesAdapter.notify(FileManager.getInstance().getFiles());
        }
    }
}
