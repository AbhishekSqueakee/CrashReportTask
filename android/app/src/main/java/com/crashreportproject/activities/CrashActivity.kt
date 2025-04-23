package com.crashreportproject.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.crashreportproject.R
import kotlin.system.exitProcess

class CrashActivity : AppCompatActivity(), OnClickListener{
    private lateinit var crashInfo: String
    private lateinit var crashReportTextView: TextView
    private lateinit var expandButton: Button
    private lateinit var shareButton: Button
    private lateinit var killButton: Button
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)
        initViews()
        crashInfo = intent.getStringExtra("crash") ?: getString(R.string.unknown_error)
    }

    // This method is used to initialize views
    private fun initViews() {
        crashReportTextView = findViewById(R.id.crash_report_text)
        expandButton = findViewById(R.id.expand_button)
        shareButton = findViewById(R.id.share_button)
        killButton = findViewById(R.id.kill_button)
        scrollView = findViewById(R.id.scroll_view)
        shareButton.setOnClickListener(this)
        expandButton.setOnClickListener(this)
        killButton.setOnClickListener(this)
    }

    // This method is used to share the crash report
    private fun shareCrashReport(context: Context, crashInfo: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crash_report_from_myapp))
            putExtra(Intent.EXTRA_TEXT, crashInfo)
        }

        val chooser = Intent.createChooser(intent, getString(R.string.send_crash_report_via))
        context.startActivity(chooser)
    }

    // This method is used to set button click listeners
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.expand_button -> {
                // Handle expand button click
                if (scrollView.isGone) {
                    crashReportTextView.text = getString(R.string.app_crashed_with_error, crashInfo)
                    scrollView.visibility = View.VISIBLE
                    expandButton.text = getString(R.string.hide_crash_report)
                } else {
                    crashReportTextView.text =
                        getString(R.string.app_crashed_with_error_click_expand_to_view_details)
                    scrollView.visibility = View.GONE
                    expandButton.text = getString(R.string.expand_crash_report)
                }
            }
            R.id.share_button -> {
                // Handle share button click
                shareCrashReport(this, crashInfo)
            }
            R.id.kill_button -> {
                // Handle kill button click
                killApp()
            }
        }
    }

    private fun killApp() {
        finishAffinity()
        exitProcess(0)
    }
}
