package com.job.github;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.job.github.api.App;
import com.job.github.models.TokenModel;

import java.io.IOException;

import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {
    private static int STATUS_OK = 200;
    private static String CLIENT_ID = "CLIENT_ID";
    private static String CLIENT_SECRET = "CLIENT_SECRET";
    private static final String TAG = "WebViewActivity";
    private WebView webView;
    private String clientId;
    private String clientSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.web_view);

        clientId = getIntent().getStringExtra(CLIENT_ID);
        clientSecret = getIntent().getStringExtra(CLIENT_SECRET);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new OAuthWebClient());
        webView.loadUrl("https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=com.job.github.oauth://token");
    }

    public static Intent newInstance(Context context, String clientId, String clientSecret) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(CLIENT_ID, clientId);
        intent.putExtra(CLIENT_SECRET, clientSecret);
        return intent;
    }

    private class OAuthWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.startsWith("com.job.github.oauth://token")) {
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
            try {
                Response<TokenModel> response = App.getApi().getToken(clientId, clientSecret, params[0]).execute();
                return response.code() == STATUS_OK ? response.body().getAccessToken() : "Error with status " + response.code();
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


}
