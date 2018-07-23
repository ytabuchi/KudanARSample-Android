package eu.kudan.ar

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.app.AlertDialog
import android.content.DialogInterface
import eu.kudan.kudan.*


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
        // Initialise the image trackable and load the image.
        imageTrackable = ARImageTrackable("Lego")
        imageTrackable.loadFromAsset("Lego.jpg") ?: return

        // Get the single instance of the image tracker.
        val trackableManager = ARImageTracker.getInstance()
        trackableManager.initialise()

        //Add the image trackable to the image tracker.
        trackableManager.addTrackable(imageTrackable)


        // Initialise the image node with our image
        val imageNode = ARImageNode("Cow.png")

        // Add the image node as a child of the trackable's world
        imageTrackable.world.addChild(imageNode)

        val textureMaterial = imageNode.material as ARTextureMaterial
        val scale = imageTrackable.width / textureMaterial.texture.width
        imageNode.scaleByUniform(scale)
    }



    public fun permissionsRequest(){
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 111)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            111 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 1個目のパーミッションのチェックだけだとIntetnetだけになっちゃうけど？？
                    // →function permissionNotSelected の名称が NoInternet なので想定内なのかも？
                } else {
                    permissionsNotSelected()
                }
            }
        }
    }

    private fun permissionsNotSelected() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permissions Requred")
        builder.setMessage("Please enable the requested permissions in the app settings in order to use this demo app")
        builder.setNeutralButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
            dialog.cancel()
            System.exit(1)
        })
        val noInternet = builder.create()
        noInternet.show()
    }

}
