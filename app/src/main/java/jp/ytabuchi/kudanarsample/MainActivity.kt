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
        key.setAPIKey("Mp2CkJrwdcY6h+2RJp+iDIWq+6Hgqj/SC6rmYRRu6+FhrP06C+MNG1skirS1Ro8+adc+BxUsxz2lrdI7whQGpzdRuMLXbxpZv1SxKBgk9K4nf7iqu21cHAY/LGid5tFxxj2KMk4Abctr3ppgPrzcFUYw2yddqjOthSvOwP/rYr/FujEUH7AjS3u3TTXGhkVu7IDDGYQmSKAbtxI/CbxokFXXxA5n1/oDCTgi0xku+j7+v00aZ4OQGkkLvMl5BsYIKzC/DDcGdanoFuKpW+K8P/gd8Tdrb0Iau6XlMbdaxPig0epvDE2iNkiNzKPsaBcOZSbEmaTouDZPHH5xAeHXnj25kB+/pObgIGz8/gKqnTQ5ALz2uoauFawSlwsI5iM+9r6MoLQLj4uV/sA/H7amJ4IQ3JMT87QpEFFaw9FvxjqgmLYoA599eqyZeegnEgyx06E7EvnpbRL56yp0wl+rHTNVEhx2/N9mjjpYXShDGmT0Ga4E4SYErqlcmc6xYQyOyxh3ZTJH0k8FPEb/2IpwUcf6hMGe4Fgq9UMAlHvFBNw4iu5qPEMjdlt4gHBp1uCAAYGDjziRutn0XoKzdH32KXeqySPAv9mLTR9HcGVzu62T7HKbIBc1yhItvq54/5qikhpMcSpwSQP5agHQwJ45iiGKnKJcgTvJFRi3U2ve+IQ=")

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
