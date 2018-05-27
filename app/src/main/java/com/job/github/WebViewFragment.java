package com.job.github;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.job.github.api.App;
import com.job.github.models.TokenModel;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Response;

public class WebViewFragment extends Fragment {
    private static int STATUS_OK = 200;
    private static String CLIENT_ID = "CLIENT_ID";
    private static String CLIENT_SECRET = "CLIENT_SECRET";
    private static final String TAG = "WebViewFragment";
    private WebView webView;
    private String clientId;
    private String clientSecret;
    private OnGetToken onGetToken;

    public interface OnGetToken {
        void onGetToken(String token);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromArguments();
    }

    private void loadFromArguments() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("There is no CLIENT_ID and CLIENT_SECRET in the arguments");
        } else {
            clientId = arguments.getString(CLIENT_ID);
            clientSecret = arguments.getString(CLIENT_SECRET);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onGetToken = (OnGetToken) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = view.findViewById(R.id.web_view);
        return view;
    }

    @Override
    public void onResume() {
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
            if (url.startsWith("com.job.github.oauth://token")) {
                String[] urls = url.split("=");
                new AccessTokenGetter(WebViewFragment.this).execute(clientId, clientSecret, urls[1]);
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d(TAG, "onReceivedError: " + error.toString());
        }
    }

    private static class AccessTokenGetter extends AsyncTask<String, Void, String> {
        private final WeakReference<WebViewFragment> webViewFragmentWeakReference;
        private ProgressDialog dialog;

        AccessTokenGetter(WebViewFragment webViewFragment) {
            webViewFragmentWeakReference = new WeakReference<>(webViewFragment);
            dialog = new ProgressDialog(webViewFragment.getContext());
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Authentication");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Response<TokenModel> response = App.getApi().getToken(params[0], params[1], params[2]).execute();
                return response.code() == STATUS_OK ? response.body().getAccessToken() : "Error with status " + response.code();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String token) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            WebViewFragment webViewFragment = webViewFragmentWeakReference.get();
            if (webViewFragment != null) {
                webViewFragment.onGetToken.onGetToken(token);
            }
        }
    }

    public static WebViewFragment newInstance(String clientId, String clientSecret) {
        Bundle args = new Bundle();
        args.putString(CLIENT_ID, clientId);
        args.putString(CLIENT_SECRET, clientSecret);

        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(args);
        return webViewFragment;
    }

}
