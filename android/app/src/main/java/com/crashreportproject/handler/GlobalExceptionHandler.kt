package com.crashreportproject.handler

import android.content.Context
import android.content.Intent
import android.util.Log
import com.crashreportproject.R

class GlobalExceptionHandler private constructor(
    private val applicationContext: Context,
    private val activityClass: Class<*>
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            // Launch the crash handler activity with the stack trace
            val intent = Intent(applicationContext, activityClass).apply {
                putExtra("crash", throwable.stackTraceToString())
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            applicationContext.startActivity(intent)

            // Give it a moment before shutting down
            Thread.sleep(1000)
        } catch (ex: Exception) {
            Log.e(applicationContext.getString(R.string.global_exception_handler),
                applicationContext.getString(R.string.crash_handler_failed), ex)
        }
    }

    // This init object is used to set the default uncaught exception handler
    companion object {
        fun init(context: Context, crashActivity: Class<*>) {
            Thread.setDefaultUncaughtExceptionHandler(
                GlobalExceptionHandler(context.applicationContext, crashActivity)
            )
        }
    }
}
