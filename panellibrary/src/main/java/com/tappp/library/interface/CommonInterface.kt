package com.tappp.library.`interface`

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

/**
 * Created by Tejas A. Prajapati on 16/12/22.
 */
class CommonInterface(context: Context?) {

    var context: Context? = context

    @JavascriptInterface
    fun showToast(toast: String?) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun getMessage(): String? {
        return "Hello From Android Native!"
    }

    @JavascriptInterface
    fun onClicked() {
        Toast.makeText(context, "Help button clicked", Toast.LENGTH_SHORT).show()
        //Log.d("HelpButton", )
    }
}