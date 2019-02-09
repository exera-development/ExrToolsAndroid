package io.exera.exrtools

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import java.util.*

/**
 * Created by Attila Janosi on 07/02/2019.
 * EXERA SOFTDEVELOP SRL
 * https://exera.io
 */

/**
 * Logcat logging
 * @param message The message
 * @param level (Optional) The log level
 */
fun Any.log(message: String, level: LogLevel = LogLevel.D) {
    if (BuildConfig.DEBUG)
        when (level) {
            LogLevel.I -> Log.i(this.javaClass.name, message)
            LogLevel.D -> Log.d(this.javaClass.name, message)
            LogLevel.E -> Log.e(this.javaClass.name, message)
            LogLevel.WTF -> Log.wtf(this.javaClass.name, message)
        }
}

enum class LogLevel {
    I, D, E, WTF
}

/**
 * Generates a new random UUID (V4)
 */
fun uuid() = UUID.randomUUID().toString()

/**
 * Wifi connection verification
 */
fun Context.isConnectedToWiFi(): Boolean {
    val connectionManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val wifiCheck: NetworkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    return wifiCheck.isConnected
}

/**
 * If the device is connected to the wifi, it will return the wifi SSID
 */
@SuppressLint("WifiManagerPotentialLeak")
fun Context.getConnectedWifiSSID(): String {
    var ssid = ""
    val connectionManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val wifiCheck: NetworkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    if (wifiCheck.isConnected) {
        val wifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifiManager.connectionInfo
        ssid = info.ssid
    }
    return ssid
}

/**
 * Shows progress dialog
 */
@SuppressLint("InflateParams")
fun Context.showProgressDialog() {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null)
    builder.setView(view)
    builder.setCancelable(false)

    dialog = builder.create()
    dialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog!!.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    dialog!!.show()
}

var dialog: AlertDialog? = null

/**
 * Hides the progress dialog
 */
fun hideProgressDialog() {
    dialog?.let { if (it.isShowing) it.dismiss() }
}


fun expand(v: View, minHeight: Int, con: Context) {
    v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    val targetHeight = v.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.layoutParams.height = 1
    if (minHeight == 0)
        v.visibility = View.VISIBLE
    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            v.layoutParams.height = if (interpolatedTime == 1f)
                LinearLayout.LayoutParams.WRAP_CONTENT
            else
                ((targetHeight - convertDpToPixel(minHeight, con)) * interpolatedTime).toInt() + convertDpToPixel(minHeight, con)
            v.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // 1dp/ms
    a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
    v.startAnimation(a)
}

fun collapse(v: View, minHeight: Int, con: Context) {
    val initialHeight = v.measuredHeight

    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                if (minHeight == 0)
                    v.visibility = View.GONE
            } else {
                v.layoutParams.height = initialHeight - ((initialHeight - convertDpToPixel(minHeight, con)) * interpolatedTime).toInt()
                //                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime)+convertDpToPixel(minHeight, con);
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // 1dp/ms
    a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
    v.startAnimation(a)
}

fun convertDpToPixel(dp: Int, context: Context): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun <T> LiveData<T>.putValue(value: T) {
    (this as MutableLiveData).value = value
}