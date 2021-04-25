package com.bihanitech.shikshyaprasasak.ui.webViewAcitivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bihanitech.shikshyaprasasak.BaseActivity;
import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.google.android.material.snackbar.Snackbar;

import java.net.InetAddress;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {
    String postUrl;
    String nextUrl;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivStatusImage)
    ImageView ivStatusImage;
    @BindView(R.id.tvDetails)
    TextView tvDetails;
    @BindView(R.id.clStatus)
    ConstraintLayout clStatus;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    boolean firstpage = true;
    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;
    @BindView(R.id.llProgressBar)
    LinearLayout llProgressBar;
    @BindView(R.id.main_content)
    ConstraintLayout mainContent;
    String language;
    SharedPrefsHelper sharedPrefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);

//        language = sharedPrefsHelper.getValue(Constant.LANGUAGE, "ne");
//        if (!sharedPrefsHelper.getValue(Constant.OLD_LANGUAGE_IS_NEPALI, false)) {
//            sharedPrefsHelper.saveValue(Constant.OLD_LANGUAGE_IS_NEPALI, true);
//            setLocale("ne");
//        }

        postUrl = intent.getExtras().getString("url");
        nextUrl = "";
        tvToolbarTitle.setText(getString(R.string.sikshya));


        webView.getSettings().setJavaScriptEnabled(true);

        ivRefresh.setVisibility(View.VISIBLE);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (isNetworkAvailable(this)) {
            checOnline();
            initWebView();
            renderPost();
        } else {
            showNetworkError();
        }


    }

    public void showNetworkError() {

        if (firstpage == true) {
            progressBar.setVisibility(View.INVISIBLE);
            webView.setVisibility(View.GONE);
            clStatus.setVisibility(View.VISIBLE);
            tvTitle.setText(getString(R.string.wifi_or_data_is_off));
            ivStatusImage.setImageResource(R.drawable.no_internet_3);
            tvDetails.setText(getString(R.string.plz_on_wifi_or_data));
        } else {
            showNetworkErrorSBar();
        }
    }

    public void showNetworkErrorSBar() {
        ConstraintLayout constraintLayout = findViewById(R.id.main_content);

        Snackbar snackbar = Snackbar
                .make(constraintLayout, getString(R.string.turn_on_network_and_retry), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isNetworkAvailable(WebViewActivity.this)) {
                            initWebView();
                            webView.loadUrl(nextUrl);
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);

                            showNetworkError();

                        }
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.setDuration(5000);
        snackbar.show();

    }

    public void showServerErrorSBar() {
        ConstraintLayout constraintLayout = findViewById(R.id.main_content);

        Snackbar snackbar = Snackbar
                .make(constraintLayout, getString(R.string.cannot_connect_to_web), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isNetworkAvailable(WebViewActivity.this)) {
                            initWebView();
                            webView.loadUrl(nextUrl);
                            progressBar.setVisibility(View.VISIBLE);

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);

                            showNetworkError();
                        }
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.setDuration(5000);
        snackbar.show();

    }

    public void showServerError() {
        if (firstpage == true) {
            progressBar.setVisibility(View.INVISIBLE);
            webView.setVisibility(View.GONE);
            clStatus.setVisibility(View.VISIBLE);
            tvTitle.setText(getString(R.string.cannot_connect_to_server));
            ivStatusImage.setImageResource(R.drawable.cloud_error_3);
            tvDetails.setText(getString(R.string.sorry_cannot_connect_to_server));

        } else {
            showServerErrorSBar();
        }
    }

    private void initWebView() {
        final WebViewActivity webViewActivity = this;
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
            boolean isPageError = false;


            public void onProgressChanged(WebView view, int progress) {

                webViewActivity.setProgress(progress * 100);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /**
                 * Check for the url, if the url is from same domain
                 * open the url in the same activity as new intent
                 * else pass the url to browser activity
                 * */

                nextUrl = url;
//                if (url.contains("google") == false) {
                if (isNetworkAvailable(WebViewActivity.this)) {
                    webView.loadUrl(url);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    showNetworkError();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                return false;
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    intent.putExtra("postUrl", url);
//                    startActivity(intent);
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
//                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                clStatus.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                if (isPageError) {
                    webView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                    showServerError();
                } else {
                    firstpage = false;
                }
            }

            public void onReceivedError(WebView view, int errorCod, String description, String failingUrl) {
                isPageError = true;
                showServerError();
            }
        });
        webView.clearCache(true);
        webView.clearHistory();

        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                    }
                    break;

                }

                return false;
            }
        });
    }

    private void renderPost() {
        webView.loadUrl(postUrl);
    }

    @OnClick(R.id.ivRefresh)
    public void onViewClicked() {
        if (isNetworkAvailable(WebViewActivity.this)) {

            checOnline();

            if (webView.getUrl() != null) {
                String s = webView.getUrl();
                progressBar.setVisibility(View.VISIBLE);
                initWebView();
                webView.loadUrl(webView.getUrl());
            } else {
                progressBar.setVisibility(View.VISIBLE);
                initWebView();
                renderPost();
            }

        } else {
            showNetworkError();
        }


    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {


        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    private void checOnline() {
        class CheckURL extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... params) {
                Boolean a = isInternetAvailable();
                return a;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (!result) {
                    clStatus.setVisibility(View.VISIBLE);
                    showServerError();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }
        CheckURL ucc = new CheckURL();
        ucc.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }
}


