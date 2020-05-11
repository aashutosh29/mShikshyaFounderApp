package com.bihanitech.shikshyaprasasak.ui.webViewAcitivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.ivmenu)
    ImageView ivMenu;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.ivTwoline)
    ImageView ivTwoline;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    String postUrl;
    private WebView webView;
    private ProgressBar progressBar;
    private float m_downX;
    //  private ImageView imgHeader;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivMenu.setVisibility(View.GONE);
        ivProfile.setVisibility(View.GONE);
        ivTwoline.setVisibility(View.GONE);
        tvToolbarTitle.setText("WebView");

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        //   imgHeader = (ImageView) findViewById(R.id.backdrop);
        Intent intent = getIntent();
        postUrl = intent.getExtras().getString("url");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                // back button pressed
            }
        });

        if (!TextUtils.isEmpty(getIntent().getStringExtra("postUrl"))) {
            postUrl = getIntent().getStringExtra("postUrl");
        }

        initWebView();
        renderPost();


        // enable / disable javascript
        // webView.getSettings().setJavaScriptEnabled(true);

        // loading url into web view
        // webView.loadUrl("http://www.google.com");

        /**
         * loading custom html into webivew
         * */
        /*
        String customHtml = "<html><body><h1>Hello, WebView</h1> <h1>Heading 1</h1><h2>Heading 2</h2><h3>Heading 3</h3>" +
                "<p>This is a sample paragraph.</p></body></html>";
        webView.loadData(customHtml, "text/html", "UTF-8");
        */

        /**
         * Enabling zoom-in controls
         * */

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);


        // Loading local html file into web view
        // webView.loadUrl("file:///android_asset/sample.html");

        /**
         * Loading custom fonts and css
         * */
        /*
        String style = "<style type='text/css'>@font-face { font-family: 'roboto'; src: url('Roboto-Light.ttf');}@font-face { font-family: 'roboto-medium'; src: url('Roboto-Medium.ttf'); }" +
                "body{color:#666;font-family: 'roboto';padding: 0.3em;}";
        style += "a{color:" + String.format("#%06X", (0xFFFFFF & ContextCompat.getColor(this, R.color.colorPrimaryDark))) + "}</style>";
        String customHtml = "<h1>Hello, WebView</h1> <h1>Heading 1</h1><h2>Heading 2</h2><h3>Heading 3</h3>" +
                "<p>This is a sample paragraph.</p>";
        String content = "<html>" + style + "<body'>" + customHtml + "</body></Html>";
        webView.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null);
        */
    }

    public void showNetworkError() {
        progressBar.setVisibility(View.INVISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(1, WebViewActivity.class.getSimpleName());
        networkErrorDFragment.show(fm, "NetworkError");
    }

    private void initWebView() {
        final WebViewActivity webViewActivity = this;
        webView.setWebChromeClient(new MyWebChromeClient(this));

        webView.setWebViewClient(new WebViewClient() {
            boolean isPageError = false;


            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                webViewActivity.setProgress(progress * 100);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /**
                 * Check for the url, if the url is from same domain
                 * open the url in the same activity as new intent
                 * else pass the url to browser activity
                 * */
                /*if (Utils.isSameDomain(postUrl, url)) {*/
                if (url.contains("google") == false) {


                    webView.loadUrl(url);
                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.putExtra("postUrl", url);
                    startActivity(intent);
                }
               /* } else {
                    // launch in-app browser i.e BrowserActivity
                    openInAppBrowser(url);
                }*/

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.VISIBLE);
                if (isPageError) {
                    webView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);


                }
            }

            public void onReceivedError(WebView view, int errorCod, String description, String failingUrl) {
                isPageError = true;
                Toast.makeText(WebViewActivity.this, "Your Internet Connection May not be active Or " + description, Toast.LENGTH_LONG).show();
                showNetworkError();
            }
        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;

                }

                return false;
            }
        });
    }

    private void renderPost() {
        webView.loadUrl(postUrl);

        // webView.loadUrl("file:///android_asset/sample.html");
    }

   /* private void openInAppBrowser(String url) {
        Intent intent = new Intent(WebViewActivity.this, BrowserActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }*/

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar txtPostTitle on scroll
     */
   /* private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Web View");
                    isShow = true;
                } else if (isShow) {
                    isShow = false;
                }
            }
        });

        collapsingToolbar.setTitle(" ");
        // loading toolbar header image
        Glide.with(getApplicationContext()).load("http://bihanitech.com/wp-content/uploads/2018/03/final-bihani-logo-2.png")
                .thumbnail(0.5f)
                //.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgHeader);
    }*/

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }


    }

}
