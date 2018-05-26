package com.job.github;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.job.github.Utils.STATUS_OK;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    private WebView webView;
    private String clientId;
    private String clientSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.web_view);

        loadClientDataFromAssets();

    }

    private void loadClientDataFromAssets() {
        Properties properties = new Properties();
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("client.properties");
            properties.load(inputStream);
            clientId = properties.getProperty("clientId");
            clientSecret = properties.getProperty("clientSecret");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new OAuthWebClient());
        webView.loadUrl("https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=com.job.github.oauth://token");
    }

    private class OAuthWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            Log.d(TAG, "shouldOverrideUrlLoading: " + url);
            if (url.startsWith("com.job.github.oauth://token")) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);
                String[] urls = url.split("=");
                new AccessTokenGetter().execute(urls[1]);
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d(TAG, "onReceivedError: " + error.toString());
        }
    }


    private class AccessTokenGetter extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = "https://github.com/login/oauth/access_token?client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + params[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request post = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse("application/json"), ""))
                        .build();
                Response response = client.newCall(post).execute();

                int status = response.code();
                if (status != STATUS_OK) {
                    return "Error with status " + status;
                } else {
                    return response.body() == null ? "Error body is empty" : getAccessToken(response.body().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String token) {
            if (token.contains("Error")) {
                Log.d(TAG, "onPostExecute: error" + token);
            } else {
                Log.d(TAG, "onPostExecute: complete" + token);
            }
        }
    }

    private String getAccessToken(String response) {
        String[] params = response.split("&");
        return params[0].split("=")[1];
    }
}
