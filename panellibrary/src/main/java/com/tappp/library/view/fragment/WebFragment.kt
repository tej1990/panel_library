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


class WebFragment() : Fragment() {

    var mWebView: WebView? = null
    var mValue: Int? =null
    val WEB_VALUE = "WEB_VALUE"

    companion object {
        const val WEB_VALUE = "WEB_VALUE"
        fun newInstance(mValue: Int): WebFragment {
            val fragment = WebFragment()
            val bundle = Bundle().apply {
                putInt(WEB_VALUE, mValue)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater?.inflate(R.layout.fragment_web, container, false)
        mValue = arguments?.getInt(WEB_VALUE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWebView = view?.findViewById(R.id.webView)
        loadUrls()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun loadUrls() {
        mWebView!!.settings.setJavaScriptEnabled(true)
        mWebView!!.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        mWebView!!.addJavascriptInterface(CommonInterface(context), "Android")
        var file = "file:android_asset/sample.html"
        if (mValue==2){
           file = "https://tappp.com/"
        }
        mWebView!!.loadUrl(file)
    }
}