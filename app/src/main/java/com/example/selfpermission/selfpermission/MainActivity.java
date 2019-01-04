package com.example.selfpermission.selfpermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickPhoneBook(View view) {
        //Determine whether you have been granted a particular permissio
        int checkStatus = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_CONTACTS);

        if (PackageManager.PERMISSION_GRANTED == checkStatus) {//you have been granted a permission
            navigatePhoneBook();
        } else {//you don`t have been granted a permission

            //Requests permissions to be granted to this application
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1001);
        }
    }

    private void navigatePhoneBook(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1001){
            if(permissions[0].equals(Manifest.permission.READ_CONTACTS)){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // approve and have permission, and open then phone book
                    navigatePhoneBook();
                }else{
                    //Gets whether you should show UI with rationale for requesting a permission.
                    boolean isShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);
                    if (!isShow){//Show the UI with rationale for requesting a permission.
                        Toast.makeText(this, "Show the UI with rationale for requesting a permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
