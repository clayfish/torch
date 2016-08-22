package in.clayfish.android.torch;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private boolean flashOn;
    private Camera camera = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.admob_app_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            Toast.makeText(MainActivity.this, "sorry, your device doesn't support flash light", Toast.LENGTH_SHORT).show();
            return;
        }

        Button button = (Button) findViewById(R.id.toggleButton);
        button.setOnClickListener(new View.OnClickListener() {
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

    private void turnFlashOff() {
        flashOn = false;
        Camera.Parameters params = getCamera().getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        getCamera().setParameters(params);
        getCamera().stopPreview();
    }

    private void turnFlashOn() {
        flashOn = true;
        Camera.Parameters params = getCamera().getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        getCamera().setParameters(params);
        getCamera().startPreview();
    }

    private Camera getCamera() {
        if (camera == null) {
            camera = Camera.open();
        }
        return camera;
    }


}


