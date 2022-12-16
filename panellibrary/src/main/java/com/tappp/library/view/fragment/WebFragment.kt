package com.tappp.library.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.panel.library.R
import com.tappp.library.`interface`.CommonInterface


class WebFragment(context: Context) : Fragment() {

    var mWebView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater?.inflate(
            R.layout.fragment_web,
            container, false
        )

        mWebView = view?.findViewById(R.id.webView)
        mWebView!!.settings.setJavaScriptEnabled(true)

        mWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }

        mWebView!!.addJavascriptInterface(
            CommonInterface(context), "Android"
        );

        val folderPath = "file:android_asset" // Get the Android assets folder path
        val fileName = "/sample.html"
        val file = folderPath + fileName
        mWebView!!.loadUrl(file)
        //mWebView!!.loadUrl("https://www.google.co.in/")
        return view
    }
}