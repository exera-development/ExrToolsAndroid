package io.exera.exrtools

import android.content.Context
import android.support.v7.app.AlertDialog

/**
 * Created by Attila Janosi on 07/02/2019.
 * EXERA SOFTDEVELOP SRL
 * https://exera.io
 */

/**
 * Shows a confirmation dialog
 *
 * @param titleId: the dialog title resource id
 * @param messageId: the dialog message resource id
 * @param handler: the dialog result handler, returns true on OK click and false on CANCEL click
 * @param positiveButtonId: (optional) the positive button text resource id
 * @param negativeButtonId: (optional) the negative button text resource id
 */
fun Context.confirm(
    titleId: Int, messageId: Int, handler: (result: Boolean) -> Unit,
    positiveButtonId: Int = android.R.string.ok, negativeButtonId: Int = android.R.string.cancel
) {
    AlertDialog.Builder(this)
        .setTitle(titleId)
        .setMessage(messageId)
        .setPositiveButton(positiveButtonId) { dialog, _ ->
            run {
                handler(true)
                dialog.dismiss()
            }
        }
        .setNegativeButton(negativeButtonId) { dialog, _ ->
            run {
                handler(false)
                dialog.dismiss()
            }
        }
        .show()
}

/**
 * Shows a confirmation dialog
 *
 * @param titleId: the dialog title resource id
 * @param messageId: the dialog message resource id
 * @param buttonTextId: (optional) the button text resource id
 */
fun showErrorDialog(titleId: Int, messageId: Int, context: Context, buttonTextId: Int = R.string.ok) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(titleId)
    builder.setMessage(messageId)

    builder.setPositiveButton(buttonTextId) { dialog, _ ->
        run {

            dialog.dismiss()
        }
    }
    builder.create().show()
}

/**
 * Shows a confirmation dialog
 *
 * @param titleId: the dialog title resource id
 * @param messageId: the dialog message resource id
 * @param buttonTextId: (optional) the button text resource id
 * @param handler: the dialog result handler
 */
fun Context.showErrorDialog(titleId: Int, messageId: Int, handler: () -> Unit, buttonTextId: Int = R.string.ok) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(titleId)
    builder.setMessage(messageId)

    builder.setPositiveButton(buttonTextId) { dialog, _ ->
        run {
            handler()
            dialog.dismiss()
        }
    }
    builder.create().show()
}

/**
 * Shows a confirmation dialog
 *
 * @param title: the dialog title
 * @param message: the dialog message
 * @param buttonText: (optional) the button text
 * @param handler: the dialog result handler
 */
fun Context.showErrorDialog(
    title: String,
    message: String,
    handler: () -> Unit,
    buttonText: String = this.getString(R.string.ok)
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)

    builder.setPositiveButton(buttonText) { dialog, _ ->
        run {
            handler()
            dialog.dismiss()
        }
    }
    builder.create().show()
}

