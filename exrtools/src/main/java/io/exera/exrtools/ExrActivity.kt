package io.exera.pushr.a_presentation.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import io.exera.exrtools.R

@SuppressLint("Registered")
open class ExrActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    /**
     * Shows progress dialog
     */
    fun showProgressDialog() {
        if (dialog != null) {
            if (dialog!!.isShowing)
                dialog!!.dismiss()
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val mailView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0) ?: return
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, mailView as ViewGroup, false)
        builder.setView(view)
        builder.setCancelable(false)

        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog?.show()
    }

    var dialog: AlertDialog? = null

    /**
     * Hides the progress dialog
     */
    fun hideProgressDialog() {
        dialog?.dismiss()
        dialog = null
    }

    override fun onDestroy() {
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            System.exit(0)
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.back_click_twice, Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }


}