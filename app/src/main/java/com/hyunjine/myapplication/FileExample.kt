package com.hyunjine.myapplication

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.*
import java.nio.file.Files

class FileExample(private val context: Context) {
    companion object {
        const val TAG = "winter"
    }

    /**
     * 어떤 형태(string, .zip etc) btyeArray로 변환하면 저장 가능
     */

    fun setNormalInternal() {
        val filename = "normalInternal"
        val fileContents = "Hello world!"

        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
    }

    fun getNormalInternal() {
        val filename = "normalInternal"
        val normalInternalFile = File(context.filesDir, filename)

        Log.d(TAG, "getNormalInternal: ${normalInternalFile.path}")
    }

    fun setCacheInternal() {
        val filename = "cacheInternal"
        File.createTempFile(filename, null, context.cacheDir)

//        Files.write(cacheInternalFile.toPath(),"gdg".toByteArray())
    }

    fun getCacheInternal() {
        val filename = "cacheInternal"
        val cacheInternalFile = File(context.cacheDir, filename)

        Log.d(TAG, "getCacheInternal: ${cacheInternalFile.name}")
    }

    // 외부의 개인 앱별 private 공간은 잘 안 쓰인다 함
    fun setNormalExternal() {
        val filename = "normalExternal.txt"
        val fileContents = "I am normalExternal"
        // 인자로 null을 넘기면 "../files/"가 리턴되며, 인자로 Environment.DIRECTORY_MUSIC를 넘기면 "../files/Music"의 형태로 리턴

        var fos: FileOutputStream? = null
        try {
            val input = fileContents.toByteArray()
            val normalExternal =
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename)
            // false -> 중복 허용 안함, true -> 중복 허용, 여러개 만들어짐
            fos = FileOutputStream(normalExternal, false)
            fos.write(input)
        } catch (e: Exception) {
            Log.e(TAG, "setNormalExternal: ${e.message}")
        } finally {
            // 필수
            fos?.close()
        }
    }

    fun getNormalExternal() {
        val filename = "normalExternal.txt"
        val normalExternal =
            File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename)

        Log.d(TAG, "getNormalExternal: ${normalExternal.path}")
    }

    fun setCacheExternal() {
        val filename = "cacheExternal"
        val cacheExternal = File(context.externalCacheDir, filename)
        //        Files.write(cacheInternalFile.toPath(),"gdg".toByteArray())
    }

    fun getCacheExternal() {
        val filename = "cacheExternal"
        val cacheExternal = File(context.externalCacheDir, filename)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun mediaImageExternal() {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "my_image.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/*")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val collection = MediaStore.Images.Media
            .getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val item = context.contentResolver.insert(collection, values)!!

        context.contentResolver.openFileDescriptor(item, "w", null).use {
            // write something to OutputStream
            FileOutputStream(it!!.fileDescriptor).use { outputStream ->
                val imageInputStream = context.resources.openRawResource(R.raw.filetest).readBytes()

                outputStream.write(imageInputStream)

                outputStream.close()
            }
        }

        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)
        context.contentResolver.update(item, values, null, null)
    }


    //경로의 텍스트 파일읽기
    fun readTextFile(file: File) {
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)

        bufferedReader.readLines().forEach() {
            Log.d(TAG, it)
        }
    }
}