package com.hyunjine.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "winter"
    }
    private lateinit var getResult: ActivityResultLauncher<Intent>

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

    fun useSAF() {
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Log.d(TAG, "onCreate: ${it.data}")
            }
        }
        getResult.launch(Intent(Intent.ACTION_OPEN_DOCUMENT))
    }
}