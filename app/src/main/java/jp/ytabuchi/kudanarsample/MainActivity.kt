package jp.ytabuchi.kudanarsample

import android.Manifest
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import eu.kudan.kudan.*
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val key = ARAPIKey.getInstance()
        key.setAPIKey("vDJw/X3pw9ehsffMwCr3qCMc+T0EZtVeJJm9apBnS9AkPQaU/du1xIxJYoTnNqXixKGLu0NOydwtF3ZE1a0qgDxa+sahCmhKBaHSSJqbp0z8TTNSMpiVn3uV069E8qcQCKPz3Z/j9PseBj1gcMNcZe31sXpVaCoQpngGVUZp9KKVFFVNmRGAE+HDxGdiB5EednNhvuupUlmdy62RaTqQtdTK1f+2Xgvl9EWzAcw8L7mZdDRx/lWa1jqRSnkEkZvw1haHX8D36+HJG+70U62ttxQq+nC7flAjDkTOFDGX7+b1vylAl1TmBIFGuOIUUULW3fRM2Z2k/drrqGLyEmxejzXhDbHnkG85adp1unx2g6Dg+uSUIwdvCligSDKzd0+gRPUobCZ9sFXAcoaS3VjXMvcV0Yllt+nK/+ERvccWD//WRQqshIjGgolv2YvtFV0vSSC5AX1H0xOYMMLQHH1Mfs78l0Tw4DKsJQMBbUbN55mdcpPe54M/iCag1BJQJxL6iHGYfqQEGxsrgK8Hteyr0upSTAxYK68ndhY/tbXToBMxJv/z8Q8TXiuymZ686yeQchZ7IMi0n3PAg6NiQT8D+BWl8p+kXkxOy/95P/5/mS1M2d3Ly6JmHDfNVPac//m3Bqul75wbMChw1FFClQSNzjxir1UDBisY7VV1dEvCYqM=")

        AppCenter.start(application, "09444c0a-9aa9-4c86-a714-7e65c4b70a7f",
                Analytics::class.java, Crashes::class.java)

        permissionsRequest()
    }

    @Suppress("UNUSED_PARAMETER")
    fun markerPageButtonClicked(view: View) {
        val markerIntent = Intent(this, MarkerActivity::class.java)
        startActivity(markerIntent)
    }

    @Suppress("UNUSED_PARAMETER")
    fun arbiPageButtonClicked(view: View) {
        val arbiIntent = Intent(this, ArbiActivity::class.java)
        startActivity(arbiIntent)
    }

    @Suppress("UNUSED_PARAMETER")
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
        builder.setPositiveButton("Set permission", DialogInterface.OnClickListener { dialog, _ ->
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
