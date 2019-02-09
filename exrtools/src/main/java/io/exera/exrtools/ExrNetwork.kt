package io.exera.exrtools

import java.lang.Exception

/**
 * Created by Attila Janosi on 08/02/2019.
 * EXERA SOFTDEVELOP SRL
 * https://exera.io
 */

object ExrNetwork {

    fun isNetworkAvailable(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: Exception) {
           log("Exception $e", LogLevel.E)
        }

        return false
    }
}