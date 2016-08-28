package in.clayfish.android.torch;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean flashOn;
    private Camera camera = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            Toast.makeText(MainActivity.this, "sorry, your device doesn't support flash light", Toast.LENGTH_SHORT).show();
            return;
        }

        ImageButton mImageButton = (ImageButton) findViewById(R.id.toggleButton);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flashOn) {
                    turnFlashOff();
                } else {
                    turnFlashOn();
                }
            }
        });
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

    private void turnFlashOff() {
        flashOn = false;
        Camera.Parameters params = getCamera().getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        getCamera().setParameters(params);
        getCamera().stopPreview();
    }

    private void turnFlashOn() {
        flashOn = true;
        Camera camera = getCamera();

        if(camera != null) {
            Camera.Parameters params = getCamera().getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            getCamera().setParameters(params);
            getCamera().startPreview();
        } else {
            Toast.makeText(this, "Torch not turned on.", Toast.LENGTH_LONG).show();
        }
    }

    private Camera getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
            } catch (Exception e) {
                Log.e(TAG, "Could not acquire camera", e);
            }
        }
        return camera;
    }

}
