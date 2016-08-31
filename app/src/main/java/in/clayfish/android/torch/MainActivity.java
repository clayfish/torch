package in.clayfish.android.torch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE = 30;

    private boolean flashOn;
    private Camera camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            Toast.makeText(MainActivity.this, "sorry, your device doesn't support flash light", Toast.LENGTH_SHORT).show();
            return;
        }

        ImageButton mImageButton = (ImageButton) findViewById(R.id.toggleButton);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleToggelButtonTap(view);
            }
        });

        if (!flashOn) {
            handleToggelButtonTap(null);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    /**
     *
     */
    private void turnFlashOff() {
        flashOn = false;
        Camera.Parameters params = getCamera().getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        getCamera().setParameters(params);
        getCamera().stopPreview();
    }

    /**
     *
     */
    private void turnFlashOn() {
        flashOn = true;
        Camera camera = getCamera();

        if (camera != null) {
            Camera.Parameters params = getCamera().getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            getCamera().setParameters(params);
            getCamera().startPreview();
        } else {
            Toast.makeText(this, "Torch not turned on.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    handleToggelButtonTap(findViewById(R.id.toggleButton));
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Can't light torch", Toast.LENGTH_LONG).show();
                }
        }
    }

    /**
     * @return
     */
    private Camera getCamera() {
        if (camera == null) {
            try {
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        Toast.makeText(this, "We need CAMERA permission to control the torch.", Toast.LENGTH_LONG).show();
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                    }
                } else {
                    camera = Camera.open();
                }
            } catch (Exception e) {
                Log.e(TAG, "Could not acquire camera", e);
            }
        }
        return camera;
    }

    /**
     * @param view
     */
    private void handleToggelButtonTap(View view) {
        if (flashOn) {
            turnFlashOff();
        } else {
            turnFlashOn();
        }
    }

}
