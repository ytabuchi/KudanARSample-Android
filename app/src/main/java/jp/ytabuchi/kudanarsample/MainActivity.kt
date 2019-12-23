package jp.ytabuchi.kudanarsample

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.app.AlertDialog
import android.content.DialogInterface
import eu.kudan.kudan.*
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val key = ARAPIKey.getInstance()
        key.setAPIKey("ovouHUtCPuTxet4uE3tealbf3MPdEfdk9oxoYDhl1pC7sXyGABQp7LwxAFLgmPqtj5ydzNq/lzDC+jfR5cK60/435sPD406FDg9bd+QVsqnUUDwBGd+B4k5DBDse6ikC61Ax3x+1GdGjgdIKu9qnOL6mNKDjQkIZXYrPeOyr2g4wyDI1v/scrIO7aXZ85EhC40wbN9vprwTYn+fo5LfTs/vTvZDyIIbo27M/5g3OPACoegqBINnh9Njla225o117bkpyJ/6GZw8B6ICYcl0Su12eS9q7ONaSX2QRAOQqwB9Q31cYFv5FaOc/W2P8/+3bv3Wh1eaRkjvmR76JDwo9bZlh4WLKHtVlWlv2CNR6vN8l5lo6IlGHY5ugYV1dP4KTImdLK1rv/j8OPxUZjKmIZJGERLYN+A0pzhbbA7smS/t0mB1WFeFfO4zG4GnSjvuIWU3av4k6R1+qBwE4sg5J9D9c6YB56OC9/510Pdg/C5U43/Lz91JfAdTdV81v8OGGH6RpKSU0AkzNSsOWMxRW3PgrUkHAPJRkt+scce7bKakxfdhHVQLbEkL2TsWB8fEJm0Bz9Pa94JY82CridPVtTi/aXe+GBPLktIIFDBM6q18yaLNASJ8kK9GsoLEfQFVxN8nBeHbMU/k2LYIQXTT0y2n1Zn7UvhnF7J3GEoN6o/E=")

        AppCenter.start(application, "09444c0a-9aa9-4c86-a714-7e65c4b70a7f",
                Analytics::class.java, Crashes::class.java)

        permissionsRequest()

    }

    fun markerPageButtonClicked(view: View) {
        val markerIntent = Intent(this, MarkerActivity::class.java)
        startActivity(markerIntent)
    }

    fun arbiPageButtonClicked(view: View) {
        val arbiIntent = Intent(this, ArbiActivity::class.java)
        startActivity(arbiIntent)
    }

    fun trackingPageButtonClicked(view: View) {
        val trackingIntent = Intent(this, TrackingActivity::class.java)
        startActivity(trackingIntent)
    }

    // Permission のリクエストを OS 標準の requestPermissions メソッドで行う
    private fun permissionsRequest(){
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 111)

        }
    }

    // ダイアログを表示して、本サンプルアプリの設定画面に遷移する
    private fun permissionsNotSelected() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permissions required")
        builder.setMessage("Please enable the requested permissions in the app settings in order to use this demo app")
        builder.setPositiveButton("Set permission", DialogInterface.OnClickListener { dialog, id ->
            dialog.cancel()
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.parse("package:eu.kudan.ar")
            startActivity(intent)
        })
        val noPermission = builder.create()
        noPermission.show()
    }

    // requestPermissions　ダイアログの結果を受け、全て許可されていなければ、permissionsNotSelected を呼び出し
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            111 -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    // パーミッションが必要な処理
                } else {
                    permissionsNotSelected()
                }
            }
        }
    }
}
