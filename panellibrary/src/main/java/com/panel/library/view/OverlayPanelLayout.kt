package com.panel.library.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.panel.library.constant.Constants
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.android.TransparencyMode
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * Created by Tejas A. Prajapati on 30/11/22.
 */
class OverlayPanelLayout : FrameLayout {

    private var flutterFragment: FlutterFragment? = null
    private val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    private var supportFragmentManager: FragmentManager? = null
    private var fragmentContainer: Int? = null
    private var flutterEngine: FlutterEngine? = null
    private var channel: MethodChannel? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    fun init(supportFragmentManager: FragmentManager, fragmentContainer: Int) {
        this.supportFragmentManager = supportFragmentManager
        this.fragmentContainer = fragmentContainer;
        flutterFragment = supportFragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?
        if (flutterFragment == null) {
            flutterEngine = FlutterEngine(context)
            flutterEngine!!.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )
            FlutterEngineCache.getInstance().put(Constants.FLUTTER_ENGINE_ID, flutterEngine)
            val flutterFragment = FlutterFragment
                .withCachedEngine(Constants.FLUTTER_ENGINE_ID)
                .renderMode(RenderMode.texture)
                .transparencyMode(TransparencyMode.transparent)
                .shouldAttachEngineToActivity(true)
                .build<FlutterFragment>()

            supportFragmentManager
                .beginTransaction()
                .add(fragmentContainer, flutterFragment, TAG_FLUTTER_FRAGMENT)
                .commit()
        }
    }

    fun start() {
        if (supportFragmentManager != null) {
            supportFragmentManager
                ?.beginTransaction()
                ?.add(fragmentContainer!!, flutterFragment!!, TAG_FLUTTER_FRAGMENT)
                ?.commit()

            //eventHandler()
        }
    }

    fun stop() {
        if (supportFragmentManager != null) {
            supportFragmentManager = null
            fragmentContainer = null
            removeView(fragmentContainer as Nothing?)
        }
    }

    private fun eventHandler() {
        channel =
            MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, Constants.FLUTTER_CHANNEL)
        channel!!.setMethodCallHandler { call, result ->
            if (mPanelDataListener != null) {
                //mPanelDataListener!!.onDataReceived(call, result)
                mPanelDataListener!!.onDataReceived(call.method, call.arguments)
            }
        }
    }

    fun passDataToFlutter(dataObject: HashMap<String, String?>) {
        channel!!.invokeMethod(Constants.SEND_TO_FLUTTER, dataObject)
    }

    interface DataListener {
        //fun onDataReceived(call: MethodCall, result: MethodChannel.Result)
        fun onDataReceived(call: String?, result: Any?)
    }

    private var mPanelDataListener: DataListener? = null

    fun subscribe(mPanelDataListener: DataListener?) {
        this.mPanelDataListener = mPanelDataListener
    }
}