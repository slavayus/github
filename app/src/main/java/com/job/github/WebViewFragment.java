package com.job.github;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.job.github.api.URLHelper;
import com.job.github.model.WebViewContractModel;
import com.job.github.model.WebViewModel;
import com.job.github.presenter.WebViewContractView;
import com.job.github.presenter.WebViewPresenter;

public class WebViewFragment extends Fragment implements WebViewContractView {
    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String CLIENT_SECRET = "CLIENT_SECRET";
    private static final String TAG = "WebViewFragment";
    private WebView mWebView;
    private String mClientId;
    private String mClientSecret;
    private OnGetToken onGetToken;
    private ProgressBar mProgressBar;
    private WebViewPresenter mPresenter;
    private ProgressDialog mDialog;


    public interface OnGetToken {
        void onGetToken(String token);
    }



    @Override
    public void loadUrl(String path) {
        mWebView.loadUrl(path);
    }

    @Override
    public void showProgressDialog() {
        mDialog = new ProgressDialog(this.getContext());
        mDialog.setMessage(getResources().getString(R.string.authentication));
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onGetToken(String token) {
        onGetToken.onGetToken(token);
    }

    @Override
    public void showErrorDialog() {
        if (getActivity() == null) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.web_view_dialog_error_title)
                .setMessage(R.string.web_view_dialog_error_message)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> getActivity().finishAffinity())
                .setCancelable(false)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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
            mClientId = arguments.getString(CLIENT_ID);
            mClientSecret = arguments.getString(CLIENT_SECRET);
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
        mWebView = view.findViewById(R.id.web_view);
        mProgressBar = view.findViewById(R.id.progressBar);

        WebViewContractModel webViewModel = new WebViewModel();
        mPresenter = new WebViewPresenter(webViewModel);
        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new OAuthWebClient());
        mWebView.setWebChromeClient(new OAuthWebChromeClient());
        mPresenter.resume(mClientId);
    }

    private class OAuthWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            mProgressBar.setProgress(progress);
        }
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
                mPresenter.authenticate(mClientId, mClientSecret, urls[1]);
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d(TAG, "onReceivedError: " + error.toString());
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
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
