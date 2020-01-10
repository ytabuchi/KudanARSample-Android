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
        key.setAPIKey("Q1jUxIjVrKEmsDn6Ww6iCyKrCmztEwwDVzViifFmFnSNvcwZJQaRAirLErsJ6l0oaKUkslF+oOWRS2Vj9PCCdX77xUpaQAMZSfOJC8WtvYSLDXA2itC4nMTP75rw6Cl9lVdnmQfuy67WC8zhpk6A993ewi6SY7Ru3eV/AOIqhnvWb3oaI2BzA8epSADWSwzHaB4VpO11QXidN7658CRhDqoZQAPxqMRswRfEAyqaDIreCQeSme3BREgNGAHtqHkjDZ4TAQLQdX0BkoYQmG8nKM+LMyNI7hPmdPCydSQli3p/VoUzfs1a1Mw2qT6oKvbMHq8NyvWsDfHyvdKzjj1a7Zgj2EiTIZnQEl4MUSMUlwa7XaclcJ4if1AJFoXChS1z1hPMiadpdESSpeuX6EUGnHLDuULJ8UudfJnIcd9y437HizS781cVdgrH5Wli+owTzUl+WXFy7POVNW4oiDoR/Ix/3nB0v+zpYf37rAOhsnDHR/LYNnl3IZsnTwM0v0a52ti49d2PLFcKXAXliqOV6MmZgs5lU477F7jeoj4BdwVBWQh3FdCaWVILpk60DH0scTX0Yb5CS6aMsmedJbDPG1h6FTO+CDFW2OfxZ7yLMzT/IlLJ3rKkUsCAnCjgwazWmVoEV4pNr8TgNGm2NEvyNRiWxN+LMHBUPnp4hn8qYwY=")

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
