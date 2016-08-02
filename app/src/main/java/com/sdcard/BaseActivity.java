package com.sdcard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Hari Prasad on 8/2/16.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public static final int REQUEST_PERMISSION_READ_STORAGE = 1;
    public static final int REQUEST_PERMISSION_WRITE_STORAGE = 2;

    public static String[] PERMISSIONS_READ_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] PERMISSIONS_WRITE_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_directory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean createDirectory(String dirName) {
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
                Toast.makeText(this, getResources().getString(R.string.toast_directory_created), Toast.LENGTH_SHORT).show();
            } else
                Log.d(TAG, "createDirectory: Already created.");
        }
        return isCreated;
    }

    public void deleteDirectory(String dirName) {
        int writeStorePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeStorePermission != PackageManager.PERMISSION_GRANTED) {
            //Requesting for permission
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_WRITE_STORAGE,
                    REQUEST_PERMISSION_WRITE_STORAGE);
        } else {
            //create file object with directory name
            File file = new File(Environment.getExternalStorageDirectory() + "/" + dirName + "/");
            if (file.exists())//check is exists
                file.delete();//delete if exists.
            Toast.makeText(this, getResources().getString(R.string.toast_directory_deleted), Toast.LENGTH_SHORT).show();
        }
    }
}
