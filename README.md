# Torch
A simple android torch app being developed by @nishthamishra7. It contains Admob ads, so some files are not included in this repository.

## Building the app
Follow these steps to build this app.

### Requirements
This is what we are currently using.

1. JDK 1.8+
3. `google-services.json`: Ask @nishthamishra7 for it.
2. Android SDK
    1. Android SDK Tools 25.1.7
    2. Android SDK Platform-tools 24.0.1
    3. Android SDK Build-tools 24.0.1
    4. Extras
        1. Android Support Repository 36
        2. Google Play Services 32
        3. Google Repository 32
        4. Google Play Licensing Library 1
        5. Google Play Billing Library 5
    5. Platforms
        1. Android 7.0 (API 24): Latest
        2. Android 6.0 (API 23)
        3. Android 2.3.1 (API 9): Oldest version which we support
        
Make sure that your Android SDK has all these components.

### Steps
1. Clone https://github.com/clayfish/torch.git
2. Modify `/local.properties` file and put path to your Android SDK.
2. Put `google-services.json` in `/app/` directory. ***DO NOT*** add this file under source control.
2. Open it using your IDE. It will try building the gradle project and take time, let it take time.


&#169; 2016 ClayFish Technologies LLP
