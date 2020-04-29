package com.app.weerpbiometric.employee;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.weerpbiometric.BaseClasses.SharedMethods;
import com.app.weerpbiometric.BaseClasses.WebService;
import com.app.weerpbiometric.Login.LoginActivity;
import com.app.weerpbiometric.R;
import com.app.weerpbiometric.Utils.AppData;
import com.app.weerpbiometric.Utils.IntentController;
import com.app.weerpbiometric.home.HomeActivity;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployeeActivity extends AppCompatActivity implements MFS100Event {
    EmployeePresenter presenter;
    Button btnInit, btnUninit, btnSyncCapture, btnStopCapture, btnMatchISOTemplate, btnClearLog;
    TextView lblMessage, tv_name;
    EditText txtEventLog;
    ImageView imgFinger;
    CheckBox cbFastDetection;
    public ProgressDialog pDialog;

    private enum ScannerAction {
        Capture, Verify
    }

    byte[] Enroll_Template;
    byte[] Verify_Template;
    private FingerData lastCapFingerData = null;
    ScannerAction scannerAction = ScannerAction.Capture;
    int timeout = 10000;
    MFS100 mfs100 = null;
    private boolean isCaptureRunning = false;
    String file_path, employee_id, employee_name, Configured, activity_name;
    DownloadZipFileTask downloadZipFileTask;
    CircleImageView profile_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        presenter = new EmployeePresenter(this);

        activity_name = getIntent().getStringExtra("activity_name");
        employee_id = AppData.current_employee_id;
        employee_name = AppData.current_employee_name;
        Configured = AppData.current_employee_is_configured;

        if (employee_id == null || employee_name == null || Configured == null) {
            IntentController.gotToActivityNoBack(this, HomeActivity.class);
        }

        // set Toolbar
        setToolBar();

        FindFormControls();
    }

    private void setToolBar() {
        AppCompatTextView tv_tooltitle = findViewById(R.id.tv_app_name);
        //tv_tooltitle.setVisibility(View.VISIBLE);
        tv_tooltitle.setText(employee_name);

        findViewById(R.id.ib_back).setVisibility(View.VISIBLE);
    }


//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            IntentController.gotToActivityNoBack(this, HomeActivity.class);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onStart() {
        super.onStart();

        requestPermission();
    }


    protected void onStop() {

        UnInitScanner();

        super.onStop();

    }


    @Override
    protected void onDestroy() {

        if (mfs100 != null) {
            mfs100.Dispose();
        }

        super.onDestroy();

    }


    public void FindFormControls() {
        btnInit = findViewById(R.id.btnInit);
        btnUninit = findViewById(R.id.btnUninit);
        btnMatchISOTemplate = findViewById(R.id.btnMatchISOTemplate);
        btnClearLog = findViewById(R.id.btnClearLog);
        lblMessage = findViewById(R.id.lblMessage);
        txtEventLog = findViewById(R.id.txtEventLog);
        imgFinger = findViewById(R.id.imgFinger);
        btnSyncCapture = findViewById(R.id.btnSyncCapture);
        btnStopCapture = findViewById(R.id.btnStopCapture);
        cbFastDetection = findViewById(R.id.cbFastDetection);
        profile_pic = findViewById(R.id.profile_pic);
        tv_name = findViewById(R.id.tv_name);

        Glide.with(this)
                .load(WebService.HOST + AppData.current_employee_image_url)
                .placeholder(R.mipmap.username)
                .dontAnimate()
                .into(profile_pic);

        tv_name.setText(AppData.current_employee_name);
    }

    public void onControlClicked(View v) {
        switch (v.getId()) {
            case R.id.btnInit:
                InitScanner();
                break;

            case R.id.btnUninit:
                UnInitScanner();
                break;

            case R.id.btnSyncCapture:
                scannerAction = EmployeeActivity.ScannerAction.Capture;
                if (!isCaptureRunning) {
                    StartSyncCapture();
                }
                break;

            case R.id.imgFinger:
                scannerAction = EmployeeActivity.ScannerAction.Capture;
                if (!isCaptureRunning) {
                    StartSyncCapture();
                }
                break;

            case R.id.btnStopCapture:
                StopCapture();
                break;

            case R.id.btnMatchISOTemplate:
                scannerAction = EmployeeActivity.ScannerAction.Verify;
                if (!isCaptureRunning) {
                    StartSyncCapture();
                }
                break;

            case R.id.btnClearLog:
                ClearLog();
                break;

            default:

                break;

        }

    }


    private void InitScanner() {

        try {

            int ret = mfs100.Init();

            if (ret != 0) {

                SetTextOnUIThread(mfs100.GetErrorMsg(ret));

            } else {

                SetTextOnUIThread("Init success");

                String info = "Serial: " + mfs100.GetDeviceInfo().SerialNo()

                        + " Make: " + mfs100.GetDeviceInfo().Make()

                        + " Model: " + mfs100.GetDeviceInfo().Model()

                        + "\nCertificate: " + mfs100.GetCertification();

                SetLogOnUIThread(info);

            }

        } catch (Exception ex) {

            Toast.makeText(this, "Init failed, unhandled exception",

                    Toast.LENGTH_LONG).show();

            SetTextOnUIThread("Init failed, unhandled exception");

        }

    }


    private void StartSyncCapture() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SetTextOnUIThread("");
                isCaptureRunning = true;
                try {
                    FingerData fingerData = new FingerData();
                    int ret = mfs100.AutoCapture(fingerData, timeout, cbFastDetection.isChecked());
                    Log.e("StartSyncCapture.RET", "" + ret);

                    if (ret != 0) {
                        SetTextOnUIThread(mfs100.GetErrorMsg(ret));

                    } else {
                        lastCapFingerData = fingerData;
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0, fingerData.FingerImage().length);

                        EmployeeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgFinger.setImageBitmap(bitmap);
                            }
                        });


                        if (fingerData.Quality() > 70) {
                            SetTextOnUIThread("Capture Success");
                            SetData2(fingerData);
                        } else {
                            SetTextOnUIThread("Captured quality was poor. Please try again");
                        }
                    }
                } catch (Exception ex) {
                    SetTextOnUIThread("Error");

                } finally {
                    isCaptureRunning = false;

                }

            }

        }).start();

    }

    public void SetData2(FingerData fingerData) {

        if (scannerAction.equals(EmployeeActivity.ScannerAction.Capture)) {
            Enroll_Template = new byte[fingerData.ISOTemplate().length];
            System.arraycopy(fingerData.ISOTemplate(), 0, Enroll_Template, 0, fingerData.ISOTemplate().length);

            SharedMethods.writeBytesToFile(Enroll_Template, employee_id, this);

            uploadFile();

        } else if (scannerAction.equals(EmployeeActivity.ScannerAction.Verify)) {

            Verify_Template = new byte[fingerData.ISOTemplate().length];
            System.arraycopy(fingerData.ISOTemplate(), 0, Verify_Template, 0, fingerData.ISOTemplate().length);

            byte[] Enroll_Template_bytes;
            File file = new File(file_path);
            if (!file.exists()) {
                SetTextOnUIThread("Finger data not exist. Connecting to the server...");

            } else {
                Enroll_Template_bytes = SharedMethods.readBytesFromFile(file, employee_id);

                int ret = mfs100.MatchISO(Enroll_Template_bytes, Verify_Template);

                if (ret < 0) {
                    SetTextOnUIThread("Error: " + ret + "(" + mfs100.GetErrorMsg(ret) + ")");
                } else {
                    if (ret >= 1400) {
                        IntentController.sendIntent(this, EmployeeServiceActivity.class);

//                        if (AppData.current_employee_attendance.equalsIgnoreCase("0")) {
//                            presenter.requestEmployeeStartAttendance();
//
//                        } else if (AppData.current_employee_attendance.equalsIgnoreCase("1")) {
//                            presenter.requestEmployeeEndAttendance();
//                        }
                    } else {
                        //SetTextOnUIThread("Finger not matched, score: " + ret);
                        SetTextOnUIThread("Finger not matched, Please try again");
                    }
                }

            }
        }
    }


    private void StopCapture() {
        try {
            mfs100.StopAutoCapture();
        } catch (Exception e) {
            SetTextOnUIThread("Error " + e.getMessage());
        }
    }


    private void UnInitScanner() {
        try {
            int ret = mfs100.UnInit();
            if (ret != 0) {
                SetTextOnUIThread(mfs100.GetErrorMsg(ret));

            } else {
                SetLogOnUIThread("Uninit Success");
                SetTextOnUIThread("Uninit Success");
                lastCapFingerData = null;
            }
        } catch (Exception e) {

            Log.e("UnInitScanner.EX", e.toString());

        }

    }

    private void ClearLog() {

        txtEventLog.post(new Runnable() {

            public void run() {

                txtEventLog.setText("");

            }

        });

    }


    private void SetTextOnUIThread(final String str) {
        lblMessage.post(new Runnable() {
            public void run() {
                lblMessage.setText(str);
            }
        });
    }


    private void SetLogOnUIThread(final String str) {
        txtEventLog.post(new Runnable() {
            public void run() {
                txtEventLog.append("\n" + str);
            }
        });
    }


    @Override

    public void OnDeviceAttached(int vid, int pid, boolean hasPermission) {

        int ret;

        if (!hasPermission) {

            SetTextOnUIThread("Permission denied");

            return;

        }

        if (vid == 1204 || vid == 11279) {

            if (pid == 34323) {

                ret = mfs100.LoadFirmware();

                if (ret != 0) {

                    SetTextOnUIThread(mfs100.GetErrorMsg(ret));

                } else {

                    SetTextOnUIThread("Load firmware success");

                }

            } else if (pid == 4101) {

                String key = "Without Key";

                ret = mfs100.Init();

                if (ret == 0) {

                    showSuccessLog(key);

                } else {

                    SetTextOnUIThread(mfs100.GetErrorMsg(ret));

                }


            }

        }

    }


    private void showSuccessLog(String key) {

        SetTextOnUIThread("Init success");

        String info = "\nKey: " + key + "\nSerial: "

                + mfs100.GetDeviceInfo().SerialNo() + " Make: "

                + mfs100.GetDeviceInfo().Make() + " Model: "

                + mfs100.GetDeviceInfo().Model()

                + "\nCertificate: " + mfs100.GetCertification();

        SetLogOnUIThread(info);

    }


    @Override

    public void OnDeviceDetached() {

        UnInitScanner();

        SetTextOnUIThread("Device removed");

    }


    @Override

    public void OnHostCheckFailed(String err) {

        try {

            SetLogOnUIThread(err);

            Toast.makeText(this, err, Toast.LENGTH_LONG).show();

        } catch (Exception ignored) {

        }

    }

    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            file_path = AppData.folder_dir + "/" + employee_id + AppData.file_format;

                            if (Configured.equalsIgnoreCase("0")) {
                                if (activity_name == null) {
                                    //verify admin
                                    Intent intent = new Intent(EmployeeActivity.this, LoginActivity.class);
                                    intent.putExtra("activity_name", "EmployeeActivity");
                                    startActivity(intent);

                                    //admin verified
                                } else if (activity_name.equalsIgnoreCase("LoginActivity")) {
                                    btnSyncCapture.setVisibility(View.VISIBLE);
                                    btnMatchISOTemplate.setVisibility(View.GONE);
                                }

                            } else if (Configured.equalsIgnoreCase("1")) {
                                File file = new File(file_path);
                                if (!file.exists()) {
                                    SetTextOnUIThread("Finger data not exist. Connecting to the server...");
                                    downloadZipFile();

                                } else {
                                    btnSyncCapture.setVisibility(View.GONE);
                                    btnMatchISOTemplate.setVisibility(View.VISIBLE);
                                }
                            }

                            if (mfs100 == null) {
                                mfs100 = new MFS100(EmployeeActivity.this);
                                mfs100.SetApplicationContext(EmployeeActivity.this);
                            } else {
                                InitScanner();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            Toast.makeText(EmployeeActivity.this, "Please Allow all the permission", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }


    //============================================== Upload File ==================================
    private void uploadFile() {
        try {
            //upload file
            String file_path = AppData.folder_dir + "/" + employee_id + AppData.file_format;
            File file = new File(file_path);
            if (file.exists()) {
                Uri file_uri = Uri.fromFile(file);
                if (file_uri.getPath() != null) {
                    uploadFile(file_uri);
                }
            } else {
                SetTextOnUIThread("File not found. Please try again");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile(Uri fileUri) {
//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Please wait");
//        pDialog.setMessage("While we are uploading file to the server");
//        pDialog.setCancelable(false);
//        pDialog.show();

        SetTextOnUIThread("Uploading file to the server. Please wait...");

        if (fileUri == null) {
            SetTextOnUIThread("Please select a file");
            return;
        }

        FileUploadService service = Servicegenerator.createService(FileUploadService.class);
        MultipartBody.Part fileBody = prepareFilePart("file", fileUri);

        RequestBody user_id = createPartFromString(employee_id);

        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("user_id", user_id);

        Call<ResponseBody> call = service.uploadFile(user_id, fileBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Log.e("Response are ", response.toString());
                //pDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String remoteResponse = response.body().string();
                        //Log.d("Leave response", remoteResponse);
                        SetTextOnUIThread("File successfully uploaded");
                        btnSyncCapture.setVisibility(View.GONE);
                        btnMatchISOTemplate.setVisibility(View.VISIBLE);

//                        JSONObject jsonObject = new JSONObject(remoteResponse);
//                        if (jsonObject.getInt("result") == 1) {
//                            Toast.makeText(ScannerActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                            //IntentController.sendIntent(LeaveApplicationActivity.this, LeaveManagementActivity.class);
//                        } else {
//                            Toast.makeText(ScannerActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(EmployeeActivity.this, "", Toast.LENGTH_SHORT).show();
                SetTextOnUIThread("File upload failed");
//                pDialog.dismiss();
            }
        });

    }

    private RequestBody createPartFromString(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        String picturePath = fileUri.getPath();
        if (picturePath != null) {
            File file = new File(picturePath);

            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(picturePath),
                            file);

            // MultipartBody.Part is used to send also the actual file name
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        }
        return null;
    }


    //============================================== Download file ==================================

    private void downloadZipFile() {
        //RetrofitInterface downloadService = createService(RetrofitInterface.class, "https://github.com/");
        //Call<ResponseBody> call = downloadService.downloadFileByUrl("anupamchugh/AnimateTextAndImageView/archive/master.zip");

        //http://kerp.kazmatechnology.in/Documents/108.zip
        RetrofitInterface downloadService = createService(RetrofitInterface.class, WebService.HOST);
        Call<ResponseBody> call = downloadService.downloadFileByUrl(WebService.BASE_URL_DOWNLOAD + employee_id + AppData.file_format);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(AppData.TAG, "Got the body for the file");

                    Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                    downloadZipFileTask = new DownloadZipFileTask();
                    downloadZipFileTask.execute(response.body());

                } else {
                    Log.d(AppData.TAG, "Connection failed " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e(AppData.TAG, t.getMessage());
            }
        });
    }

    public <T> T createService(Class<T> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder().build())
                .build();
        return retrofit.create(serviceClass);
    }

    private class DownloadZipFileTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(ResponseBody... urls) {
            //Copy you logic to calculate progress and call
            saveToDisk(urls[0]);
            return null;
        }

        protected void onProgressUpdate(Pair<Integer, Long>... progress) {

            Log.d("API123", progress[0].second + " ");

            if (progress[0].first == 100) {
                //Toast.makeText(getApplicationContext(), "File downloaded successfully", Toast.LENGTH_SHORT).show();

                SetTextOnUIThread("File downloaded successful.");

                btnSyncCapture.setVisibility(View.GONE);
                btnMatchISOTemplate.setVisibility(View.VISIBLE);
            }

            if (progress[0].second > 0) {
                int currentProgress = (int) ((double) progress[0].first / (double) progress[0].second * 100);
                //progressBar.setProgress(currentProgress);
                //txtProgressPercent.setText("Progress " + currentProgress + "%");

                SetTextOnUIThread("Progress... " + currentProgress + "%");

            }

            if (progress[0].first == -1) {
                //Toast.makeText(getApplicationContext(), "Download failed", Toast.LENGTH_SHORT).show();
                SetTextOnUIThread("Download failed");
            }

        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(String result) {

        }

    }

    private void saveToDisk(ResponseBody body) {
        try {
            //File destinationFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
            File destinationFile = new File(file_path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();
                Log.d(AppData.TAG, "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                    Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
                    downloadZipFileTask.doProgress(pairs);
                    Log.d(AppData.TAG, "Progress: " + progress + "/" + fileSize + " >>>> " + (float) progress / fileSize);
                }

                outputStream.flush();

                Log.d(AppData.TAG, destinationFile.getParent());
                Pair<Integer, Long> pairs = new Pair<>(100, 100L);
                downloadZipFileTask.doProgress(pairs);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                downloadZipFileTask.doProgress(pairs);
                Log.d(AppData.TAG, "Failed to save the file!");
                return;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(AppData.TAG, "Failed to save the file!");
            return;
        }
    }
}
