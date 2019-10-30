package androidstudio.myclassroom.com.androidstudio.home.tab_library.storagechooser;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codekidlabs.storagechooser.Content;
import com.codekidlabs.storagechooser.StorageChooser;

import androidstudio.myclassroom.com.androidstudio.R;

public class MainActivityStorageChooser extends AppCompatActivity {

    TextView txtPath;
    private int STORAGE_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_storage_chooser);

        Fade fade = new Fade();
        fade.setDuration(400);
        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setEnterTransition(fade);

        txtPath = (TextView) findViewById(R.id.txtPath);
    }

    public void openChooser(View view) {
        if (!isStorageReadable()) {
            requestStoragePermission();
        } else {
            chooser();
        }
    }

    //We are calling this method to check the permission status
    private boolean isStorageReadable() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(getApplicationContext(), "Permission Required to Access Storage", Toast.LENGTH_SHORT).show();
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooser();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the Storage permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void chooser() {
        Content c = new Content();
        c.setInternalStorageText("Internal Storage");
        c.setCancelLabel("Cancel");
        c.setSelectLabel("Choose");
        c.setOverviewHeading("Choose Drive");

        StorageChooser chooser = new StorageChooser.Builder()
                .withActivity(MainActivityStorageChooser.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .setType(StorageChooser.DIRECTORY_CHOOSER)   //.setType(StorageChooser.FILE_PICKER) to Pick a Path of a file
                .showHidden(false) //.showHidden(true)
                .allowCustomPath(true)
                .withPredefinedPath(Environment.getExternalStorageDirectory().toString())
                .setDialogTitle("Choose Directory")
                .withContent(c)
                .build();

        chooser.show();

        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                txtPath.setText(path);
            }
        });
    }
}
