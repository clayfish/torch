package in.clayfish.android.torch;

import android.app.Application;
import android.content.pm.PackageManager;

import java.util.Locale;

/**
 * @since 14/12/2016
 */
public class TorchApp extends Application {

    private static final String APP_ID = "in.clayfish.android.torch";

    /**
     * @return
     */
    public String getVersionName() {
        try {
            return getPackageManager().getPackageInfo(APP_ID, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @return
     */
    public int getVersionNumber() {
        try {
            return getPackageManager().getPackageInfo(APP_ID, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * @return
     */
    public String getVersionString() {
        return String.format(Locale.ENGLISH, "v%s (%d)", getVersionName(), getVersionNumber());
    }
}
