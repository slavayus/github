package com.job.github;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
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

import retrofit2.Call;
import retrofit2.Callback;
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
        webView.loadUrl(URLHelper.BASE_URL + URLHelper.AUTHORIZE_URL + "?client_id=" + clientId + "&redirect_uri=" + URLHelper.REDIRECT_URL);
    }

    private class OAuthWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.startsWith(URLHelper.REDIRECT_URL)) {
                String[] urls = url.split("=");
                authenticate(urls[1]);
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d(TAG, "onReceivedError: " + error.toString());
        }
    }

    private void authenticate(String url) {
        final ProgressDialog dialog = new ProgressDialog(this.getContext());
        dialog.setMessage(getResources().getString(R.string.authentication));
        dialog.show();

        App.getApi().getToken(clientId, clientSecret, url).enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                dismissDialog();
                onGetToken.onGetToken(response.code() == STATUS_OK ? response.body().getAccessToken() : "Error with status " + response.code());
            }

            // TODO: 5/27/18 add onFailure method to OnGetToken interface
            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                dismissDialog();
                onGetToken.onGetToken("ERROR");
            }

            private void dismissDialog() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

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
