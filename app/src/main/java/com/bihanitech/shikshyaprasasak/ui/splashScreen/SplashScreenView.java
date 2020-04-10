package com.bihanitech.shikshyaprasasak.ui.splashScreen;

import java.io.FileOutputStream;

/**
 * Created by dilip on 3/5/18.
 */

public interface SplashScreenView {
    void showProgressBar();

    void sendToHome();

    void showNetworkError();

    void setMetaDataDownloaded();

    FileOutputStream getSchoolLogoFile(String schoolTag);

    void setLogoDownloaded();

    void retry();

    void abort();

    void showServerError();
}
