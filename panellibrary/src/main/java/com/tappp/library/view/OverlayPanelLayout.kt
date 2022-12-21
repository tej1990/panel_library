package com.tappp.library.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.panel.library.R
import com.tappp.library.constant.Constants
import com.tappp.library.model.PanelSettingDataModel
import com.tappp.library.view.fragment.ImageFragment
import com.tappp.library.view.fragment.WebFragment
import org.json.JSONObject

/**
 * Created by Tejas A. Prajapati on 30/11/22.
 */
class OverlayPanelLayout : FrameLayout {

    private val TAG_FLUTTER_FRAGMENT = "flutter_fragment"

    /*private var flutterFragment: FlutterFragment? = null
    private val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    private var fragmentContainer: Int? = null
    private var flutterEngine: FlutterEngine? = null
    private var channel: MethodChannel? = null*/
    private var mFragment: Fragment = ImageFragment()
    private var supportFragmentManager: FragmentManager? = null
    private var tapppViewContainer: Int = 0
    private lateinit var panelData: JSONObject

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

    fun init(mViewEnum: String, supportFragmentManager: FragmentManager, tapppViewContainer: Int) {
        this.supportFragmentManager = supportFragmentManager
        this.tapppViewContainer = tapppViewContainer
        if (mViewEnum.equals(Constants.LOCAL_WEB_VIEW)) {
            mFragment = WebFragment.newInstance(1)
        } else if (mViewEnum.equals(Constants.S3_WEB_VIEW)) {
            mFragment = WebFragment.newInstance(2)
        } else if (mViewEnum.equals(Constants.LIBRARY_CALENDER)) {
            mFragment = WebFragment.newInstance(3)
        } else if (mViewEnum.equals(Constants.LIBRARY_PANEL)) {
            mFragment = WebFragment.newInstance(5)
        }
        start()
        /*this.supportFragmentManager = supportFragmentManager
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
                    .shouldAttachEngineToActivity(false)
                    .build<FlutterFragment>()

                supportFragmentManager
                    .beginTransaction()
                    .add(fragmentContainer, flutterFragment, TAG_FLUTTER_FRAGMENT)
                    .commit()
            }*/
    }

    fun init(panelData: JSONObject, panelSetting: PanelSettingDataModel) {
        this.supportFragmentManager = panelSetting.supportManager as FragmentManager?
        this.tapppViewContainer = panelSetting.containerView as Int

        this.panelData = panelData
        if (panelData.length() > 0) {
            val mViewEnum = panelData.get("DISPLAY_OPTION")
            if (mViewEnum.equals(Constants.LOCAL_WEB_VIEW)) {
                mFragment = WebFragment.newInstance(1)
            } else if (mViewEnum.equals(Constants.S3_WEB_VIEW)) {
                mFragment = WebFragment.newInstance(2)
            } else if (mViewEnum.equals(Constants.LIBRARY_CALENDER)) {
                mFragment = WebFragment.newInstance(3)
            } else if (mViewEnum.equals(Constants.LIBRARY_PANEL)) {
                mFragment = WebFragment.newInstance(5)
            }
        } else {
            mFragment = WebFragment.newInstance(5)
        }

    }

    fun start() {
        if (supportFragmentManager != null) {
            supportFragmentManager!!
                .beginTransaction()
                .add(tapppViewContainer, mFragment, TAG_FLUTTER_FRAGMENT)
                .commit()
        }
    }

    fun stop() {
        if (supportFragmentManager != null) {
            supportFragmentManager!!.beginTransaction().remove(mFragment).commit()
        }
    }

    fun subscribe(eventData: JSONObject, mPanelDataListener: DataListener?) {
        this.mPanelDataListener = mPanelDataListener
    }

    fun unsubscribe(mPanelDataListener: DataListener?) {
        this.mPanelDataListener = mPanelDataListener
    }

    private fun eventHandler() {
        /* channel =
             MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, Constants.FLUTTER_CHANNEL)
         channel!!.setMethodCallHandler { call, result ->
             if (mPanelDataListener != null) {
                 //mPanelDataListener!!.onDataReceived(call, result)
                 mPanelDataListener!!.onDataReceived(call.method, call.arguments)
             }
         }*/
    }

    fun passDataToFlutter(dataObject: HashMap<String, String?>) {
        //channel!!.invokeMethod(Constants.SEND_TO_FLUTTER, dataObject)
    }

    interface DataListener {
        //fun onDataReceived(call: MethodCall, result: MethodChannel.Result)
        fun onDataReceived(call: String?, result: Any?)
    }

    private var mPanelDataListener: DataListener? = null
}