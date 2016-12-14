package in.clayfish.android.torch.manager.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import in.clayfish.android.torch.manager.TorchManager;
import in.clayfish.android.torch.manager.TorchManagerAdapter;

/**
 * @author shuklaalok7
 * @since 14/12/2016
 */
public class TorchManagerImpl implements TorchManager {
    private static final String TAG = TorchManagerImpl.class.getSimpleName();

    private boolean flashOn;
    private Camera mCamera = null;
    private CameraManager mCameraManager;
    private Activity mActivity;

    public TorchManagerImpl(Activity activity) {
        this.mActivity = activity;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        }
    }

    @Override
    public void turnOff() {
        flashOn = false;
        Camera.Parameters params = getCamera().getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        getCamera().setParameters(params);
        getCamera().stopPreview();
    }

    @Override
    public void turnOn() {
        Camera camera = getCamera();

        if (camera != null) {
            flashOn = true;
            Camera.Parameters params = getCamera().getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            getCamera().setParameters(params);
            getCamera().startPreview();
        } else {
            Toast.makeText(mActivity, "Torch not turned on.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void toggle() {
        if (flashOn) {
            turnOff();
        } else {
            turnOn();
        }
    }

    @Override
    public boolean isFlashOn() {
        return flashOn;
    }

    /**
     * @return
     */
    private Camera getCamera() {
        if (mCamera == null) {
            try {
                // Here, thisActivity is the current mActivity
                if (ContextCompat.checkSelfPermission(mActivity,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat
                            .shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        Toast.makeText(mActivity, "We need CAMERA permission to control the torch.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(mActivity,
                                new String[]{Manifest.permission.CAMERA},
                                ((TorchManagerAdapter) mActivity).getCameraRequestCode());
                    }
                } else {
                    mCamera = Camera.open();
                }
            } catch (Exception e) {
                Log.e(TAG, "Could not acquire mCamera", e);
            }
        }
        return mCamera;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
