package com.smallcat.theworld.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.ui.widget.ProgressView
import com.smallcat.theworld.utils.LogUtil
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.normal_toolbar.*

class WebActivity : RxActivity() {

    private var url: String? = ""
    private lateinit var mProgressBar: ProgressView
    private lateinit var mHandler: Handler
    private var mWebView: WebView? = null

    companion object {
        private const val TAG = "WebActivity"
        fun getIntent(mContext: Context, url: String) =
                Intent(mContext, WebActivity::class.java).apply {
                    putExtra(TAG, url)
                }
    }

    override val layoutId: Int
        get() = R.layout.activity_web

    override fun initData() {
        mProgressBar = ProgressView(mContext)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mProgressBar.layoutParams = layoutParams
        mProgressBar.visibility = View.GONE
        ll_layout.addView(mProgressBar)
        mHandler = Handler()

        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mWebView = WebView(applicationContext)
        mWebView?.layoutParams = params
        ll_layout.addView(mWebView)
        url = intent.getStringExtra(TAG)

        mWebView?.setLayerType(View.LAYER_TYPE_HARDWARE,null)
        //WebView.setWebContentsDebuggingEnabled(true)  //开启调试模式
        LogUtil.e(url)
        initSetting()
        mWebView!!.loadUrl(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initSetting() {
        val webSetting = mWebView!!.settings
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = false
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.loadWithOverviewMode = true // 缩放至屏幕的大小
        webSetting.setSupportMultipleWindows(true)
        webSetting.domStorageEnabled = true
        webSetting.setAppCacheEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        mWebView?.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    mProgressBar.setProgress(100)
                    mHandler.postDelayed(runnable, 200)//0.2秒后隐藏进度条
                } else if (mProgressBar.visibility == View.GONE) {
                    mProgressBar.visibility = View.VISIBLE
                }
                //不断更新进度
                mProgressBar.setProgress(newProgress)
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                tv_title.text = title
            }
        }

        mWebView?.webViewClient = object : WebViewClient(){

            /**
             * 处理网页不以http，https开头的情况
             */
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                LogUtil.e(url)
                if (url == null)
                    return false
                try{
                    if(!url.startsWith("http://") && !url.startsWith("https://")){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                }catch (e: Exception){//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view?.loadUrl(url)
                return true
            }

            override fun onReceivedSslError(p0: WebView?, p1: SslErrorHandler?, p2: SslError?) {
                p1?.proceed()
                super.onReceivedSslError(p0, p1, p2)
            }

            /*   //处理网页加载失败时
              override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                  onReceivedError(view, error.errorCode, error.description.toString(), request.url.toString())
              }

              override fun onReceivedError(p0: WebView?, p1: Int, p2: String?, p3: String?) {
                  super.onReceivedError(p0, p1, p2, p3)
                  showErrorView()
              }*/

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                // 关闭图片加载阻塞
                view.settings.blockNetworkImage = false
            }

            override fun onScaleChanged(view: WebView, oldScale: Float, newScale: Float) {
                super.onScaleChanged(view, oldScale, newScale)
                view.requestFocus()
                view.requestFocusFromTouch()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView!!.canGoBack()) {
            //如果可以回退
            mWebView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        mWebView?.destroy()
        ll_layout.removeView(mWebView)
        mWebView = null
        super.onDestroy()
    }

    /**
     * 刷新界面（此处为加载完成后进度消失）
     */
    private val runnable = Runnable { mProgressBar.visibility = View.GONE }
}
