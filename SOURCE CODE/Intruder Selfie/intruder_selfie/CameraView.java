package com.app.theshineindia.intruder_selfie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

import com.app.theshineindia.R;
import com.app.theshineindia.baseclasses.SharedMethods;
import com.app.theshineindia.utils.AppData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraView extends Activity implements SurfaceHolder.Callback, OnClickListener {
    private static final String TAG = "1111";
    Camera mCamera;
    boolean mPreviewRunning = false;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.e(TAG, "onCreate");

        setContentView(R.layout.cameraview);

        mSurfaceView = findViewById(R.id.surface_camera);

        // mSurfaceView.setOnClickListener(this);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setKeepScreenOn(true);

        // mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //CameraView.this.moveTaskToBack(true);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }

    @SuppressLint("HandlerLeak")
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.e(TAG, "surfaceChanged");

        // XXX stopPreview() will crash if preview is not running
        if (mPreviewRunning) {
            mCamera.stopPreview();
        }

        Camera.Parameters p = mCamera.getParameters();

        mCamera.setParameters(p);

        mCamera.startPreview();
        mPreviewRunning = true;

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mCamera.takePicture(null, null, mPictureCallback);
            }
        }.sendEmptyMessageDelayed(0, 500);

//        Camera.Parameters p = mCamera.getParameters();
//        if (p.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//            p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//        }
//        mCamera.setParameters(p);
//
//        mCamera.startPreview();
//        mPreviewRunning = true;
//
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                // Do Whatever
//                mCamera.takePicture(null, null, mPictureCallback);
//            }
//        });
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed");
        // mCamera.stopPreview();
        // mPreviewRunning = false;
        // mCamera.release();

        stopCamera();
    }

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    public void onClick(View v) {
        mCamera.takePicture(null, mPictureCallback, mPictureCallback);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated");

        int i = findFrontFacingCamera();

        if (i > 0) ;
        while (true) {
            try {
                this.mCamera = Camera.open(i);
                try {
                    this.mCamera.setPreviewDisplay(holder);
                    return;
                } catch (IOException localIOException2) {
                    stopCamera();
                    return;
                }
            } catch (RuntimeException localRuntimeException) {
                localRuntimeException.printStackTrace();
                if (this.mCamera == null) continue;
                stopCamera();
                this.mCamera = Camera.open(i);
                try {
                    this.mCamera.setPreviewDisplay(holder);
                    Log.d("HiddenEye Plus", "Camera open RE");
                    return;
                } catch (IOException localIOException1) {
                    stopCamera();
                    localIOException1.printStackTrace();
                    return;
                }

            } catch (Exception localException) {
                if (this.mCamera != null) stopCamera();
                localException.printStackTrace();
                return;
            }
        }
    }

    private void stopCamera() {
        if (this.mCamera != null) {
            /*
             * this.mCamera.stopPreview(); this.mCamera.release(); this.mCamera = null;
             */
            this.mPreviewRunning = false;
        }
    }

    private int findFrontFacingCamera() {
        int i = Camera.getNumberOfCameras();
        for (int j = 0; ; j++) {
            if (j >= i) return -1;
            Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(j, localCameraInfo);
            if (localCameraInfo.facing == 1) return j;
        }
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            if (data != null) {
                // Intent mIntent = new Intent();
                // mIntent.putExtra("image",imageData);

                mCamera.stopPreview();
                mPreviewRunning = false;
                mCamera.release();

                try {
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                            data.length, opts);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int newWidth = 500;
                    int newHeight = 500;

                    // calculate the scale - in this case = 0.4f
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;

                    // createa matrix for the manipulation
                    Matrix matrix = new Matrix();
                    // resize the bit map
                    matrix.postScale(scaleWidth, scaleHeight);
                    // rotate the Bitmap
                    matrix.postRotate(-90);
                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                    // you can create a new file name "test.jpg" in sdcard
                    // folder.

                    createDirIfNotExists(AppData.folder_name);

                    File f = new File(Environment.getExternalStorageDirectory()
                            + File.separator
                            + AppData.folder_name
                            + File.separator
                            + SharedMethods.getCurrentDateTime()
                            + ".jpg");

                    Log.d(TAG, "file : " + f);

                    f.createNewFile();
                    // write the bytes in file
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());

                    // remember close de FileOutput
                    fo.close();

                    // SEND INTRUDER LOCATION AND IMAGE TO ADMIN
                    String image_str = SharedMethods.convertToString(resizedBitmap);
                    new IntruderSelfiePresenter(CameraView.this).requestUploadSelfie(image_str);

                    CameraView.this.moveTaskToBack(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // StoreByteImage(mContext, imageData, 50,"ImageName");
                // setResult(FOTO_MODE, mIntent);
                setResult(585);
                finish();
            }
        }
    };

    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(TAG, "Problem creating Image folder");
                ret = false;
            }
        }
        return ret;
    }
}