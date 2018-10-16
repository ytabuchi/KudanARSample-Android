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
import eu.kudan.kudan.ARVideoNode
import eu.kudan.kudan.ARVideoTexture

class MarkerActivity : ARActivity() {

    private lateinit var imageTrackable: ARImageTrackable
    private lateinit var videoNode: ARVideoNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_marker)
    }

    override fun setup() {

        addImageTrackable()

        addImageNode()
        addModelNode()
        addVideoNode()
    }


    private fun addImageTrackable(){

        // ARImageTrackable をインスタンス化して画像を読み込み
        imageTrackable = ARImageTrackable("Lego")
        imageTrackable.loadFromAsset("lego.jpg")

        // ARImageTracker のインスタンスを取得して初期化
        val trackableManager = ARImageTracker.getInstance()
        trackableManager.initialise()

        // imageTrackable を ARImageTracker に追加
        trackableManager.addTrackable(imageTrackable)
    }

    
    private fun addImageNode(){

        // ARImageNode を画像を指定して初期化
        val imageNode = ARImageNode("cow.png")

        // imageNode のサイズを Trackable のサイズに合わせる
        val textureMaterial = imageNode.material as ARTextureMaterial
        val scale = imageTrackable.width / textureMaterial.texture.width
        imageNode.scaleByUniform(scale)

        // imageNode を trackable の world に追加
        imageTrackable.world.addChild(imageNode)

        // 初期状態で非表示
        imageNode.visible = false
    }

    private fun addModelNode(){

        // モデルのインポート
        val modelImporter = ARModelImporter()
        modelImporter.loadFromAsset("ben.jet")
        val modelNode = modelImporter.node as ARModelNode

        // モデルのテクスチャーを読み込み
        val texture2D = ARTexture2D()
        texture2D.loadFromAsset("bigBenTexture.png")

        // ARLightMaterial　を作成してテクスチャーとアンビエントを指定
        val material = ARLightMaterial()
        material.setTexture(texture2D)
        material.setAmbient(0.8f, 0.8f, 0.8f)

        // モデルの全 meshNode に material を追加
        modelImporter.meshNodes.forEach { meshNode ->
            meshNode.material = material
        }

        // modelNode の向きと大きさを指定
        modelNode.rotateByDegrees(90f, 1f, 0f, 0f)
        modelNode.scaleByUniform(0.25f)

        // modelNode を trackable の world に追加
        imageTrackable.world.addChild(modelNode)

        // 初期状態で非表示
        modelNode.visible = false
    }

    private fun addVideoNode(){

        // ARVideoTexture を mp4 ファイルで初期化
        val videoTexture = ARVideoTexture()
        videoTexture.loadFromAsset("cloud.mp4")

        // ARVideoTexture で ARVideoNode をインスタンス化
        videoNode = ARVideoNode(videoTexture)

        // videoNode のサイズを Trackable のサイズに合わせる
        val scale = imageTrackable.width / videoTexture.width
        videoNode.scaleByUniform(scale)

        // videoNode を trackable の world に追加
        imageTrackable.world.addChild(videoNode)

        // 初期状態で非表示
        videoNode.visible = false
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

    fun showVideoButtonClicked(view: View){
        hideAll()
        videoNode.videoTexture.reset()
        videoNode.videoTexture.start()
        imageTrackable.world.children[2].visible = true
    }

}