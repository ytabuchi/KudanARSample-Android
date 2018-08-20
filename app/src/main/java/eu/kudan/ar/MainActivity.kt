package eu.kudan.ar

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.app.AlertDialog
import android.content.DialogInterface
import eu.kudan.kudan.*
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import eu.kudan.kudan.ARMeshNode
import eu.kudan.kudan.ARLightMaterial
import eu.kudan.kudan.ARTexture2D
import eu.kudan.kudan.ARModelNode
import eu.kudan.kudan.ARModelImporter




class MainActivity : ARActivity() {

    private lateinit var imageTrackable: ARImageTrackable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val key = ARAPIKey.getInstance()
        key.setAPIKey("agWZcpYLYjBxCbWf2qZx6k+PWISqeGtFCqKaZwYtwS+kdn1HKiQAmsJ55STRBe9BqCw3VwG6qL+ESI5ntTF/iV/uekLG3PCokaUE0/uTzqhaYlxRdmuNBIduzBCjq3mV2na+gy3ffHH9Ipc7eIN0geTj3p+ppsmK0U399iGmN38ndIh6k2y16cByWIecMSU3yw3Ztw7gHRqf83hVhZ5T2ACGK4SNkQhhdKp+CTaR5W3amYCJBgwumqFqNFyI9UniuMk70T/cQObRQum2U51OjjbMfmEAwIBt8Q8jD2yACzye6K4/1O4pZhbGEbiDeLrAfxqMwBAe5o6vnYIilGNnpDhfi3wOHhRaqtLOVvB58GUIFTnAPvmYFVnLWRJmCUZ9FJNDyX3ALCl/alFEWh+A/a6NFjcwLGKI9drPuGG4ONFg4p0l+p3b9DZoLzszlmWAflI/UFzQa++kQn3/sclO9i0vPnpi0LWoABm5vGswLVAIX/0k6384GXxfkADI6fjGtf62XJ5ImaVDiiREa9mabWEQGoifghQG1sGNDYgBIYEpiaLsVzOfTALpe20Q7kFCMjedJImQhhuLtEK1BXfXJEed1QqUOsG9IeKxKk28GbOtOF9w3yrSF3gnJslzZxF2kEF3C6ckog8byagS+4p37FJmbpPsiKNH1Qm0LuouGcQ=")

        permissionsRequest()
    }

    override fun setup() {

        addImageTrackable()

        addImageNode()
        addModelNode()
    }


    private fun addImageTrackable(){

        // Initialise the image trackable and load the image.
        imageTrackable = ARImageTrackable("Lego")
        imageTrackable.loadFromAsset("lego.jpg") ?: return

        // Get the single instance of the image tracker.
        val trackableManager = ARImageTracker.getInstance()
        trackableManager.initialise()

        //Add the image trackable to the image tracker.
        trackableManager.addTrackable(imageTrackable)
    }

    private fun addImageNode(){

        // Initialise the image node with our image
        val imageNode = ARImageNode("cow.png")

        // Add the image node as a child of the trackable's world
        imageTrackable.world.addChild(imageNode)

        // imageNode のサイズを Trackable のサイズに合わせる
        val textureMaterial = imageNode.material as ARTextureMaterial
        val scale = imageTrackable.width / textureMaterial.texture.width
        imageNode.scaleByUniform(scale)

        // 初期状態で非表示
        imageNode.visible = false
    }

    private fun addModelNode(){

        // Import model
        val modelImporter = ARModelImporter()
        modelImporter.loadFromAsset("ben.jet")
        val modelNode = modelImporter.node as ARModelNode

        // Load model texture
        val texture2D = ARTexture2D()
        texture2D.loadFromAsset("bigBenTexture.png")

        // Apply model texture to model texture material
        val material = ARLightMaterial()
        material.setTexture(texture2D)
        material.setAmbient(0.8f, 0.8f, 0.8f)

        // Apply texture material to models mesh nodes
        for (meshNode in modelImporter.meshNodes) {
            meshNode.material = material
        }

        modelNode.rotateByDegrees(90f, 1f, 0f, 0f)
        modelNode.scaleByUniform(0.25f)

        // Add model node to image trackable
        imageTrackable.world.addChild(modelNode)

        // 初期状態で非表示
        modelNode.visible = false
    }

    // 全ての Node を非表示
    private fun hideAll(){
        val nodes = imageTrackable.world.children
        for (node in nodes)
            node.visible = false
    }

    fun clearAllButtonClicked(view: View){
        hideAll()
    }

    fun showImageButtonClicked(view: View){
        hideAll()
        imageTrackable.world.children[0].visible = true
    }

    fun showModelButtonClicked(view: View){
        hideAll()
        imageTrackable.world.children[1].visible = true
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
