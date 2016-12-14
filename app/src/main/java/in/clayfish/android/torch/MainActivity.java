package in.clayfish.android.torch;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import in.clayfish.android.torch.manager.TorchManager;
import in.clayfish.android.torch.manager.TorchManagerAdapter;
import in.clayfish.android.torch.manager.impl.TorchManagerImpl;

public class MainActivity extends AppCompatActivity implements TorchManagerAdapter {
    private static final int CAMERA_REQUEST_CODE = 30;

    private TorchManager mTorchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTorchManager = new TorchManagerImpl(this);

        boolean hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            Toast.makeText(MainActivity.this, getString(R.string.sorry), Toast.LENGTH_SHORT).show();
            return;
        }

        ImageButton mImageButton = (ImageButton) findViewById(R.id.toggleButton);
        mImageButton.setLongClickable(true);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTorchManager.toggle();
            }
        });
        mImageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String message = getString(R.string.turn_on);
                if (mTorchManager.isFlashOn()) {
                    message = getString(R.string.turn_off);
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                return true;
            }
        });

        mTorchManager.turnOn();

        TextView textView = (TextView) findViewById(R.id.version_text);
        textView.setText(((TorchApp) getApplication()).getVersionString());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mTorchManager.toggle();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Can't light torch", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public int getCameraRequestCode() {
        return CAMERA_REQUEST_CODE;
    }
}
