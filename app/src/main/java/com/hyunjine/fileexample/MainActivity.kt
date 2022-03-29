package com.hyunjine.fileexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "winter"
    }
    private val fileExample: FileExample by lazy { FileExample(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    private fun logFile() {
        filesDir.listFiles()?.forEach {
            Log.d(TAG, "Internal: ${it.path}")
        }
        getExternalFilesDir(null)?.listFiles()?.forEach {
            Log.d(TAG, "External: ${it.path}")
        }
    }
}