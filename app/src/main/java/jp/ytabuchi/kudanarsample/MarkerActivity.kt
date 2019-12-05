package jp.ytabuchi.kudanarsample

import android.os.Bundle
import android.util.Log
import eu.kudan.kudan.*
import android.view.View
import eu.kudan.kudan.ARLightMaterial
import eu.kudan.kudan.ARTexture2D
import eu.kudan.kudan.ARModelNode
import eu.kudan.kudan.ARModelImporter
import eu.kudan.kudan.ARVideoNode
import eu.kudan.kudan.ARVideoTexture
import eu.kudan.kudan.ARImageTrackable

class MarkerActivity : ARActivity(), ARImageTrackableListener {

    private lateinit var imageTrackable: ARImageTrackable
    private lateinit var secondImageTrackable: ARImageTrackable
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

        addSecondImageNode()
        addSecondModelNode()

        // Listnerを追加
        imageTrackable.addListener(this)
    }

    // ARImageTrackableListener インターフェースの実装。これらはトラッキングイベントが発生すると呼ばれます。
    override fun didDetect(imageTrackable: ARImageTrackable) {
        Log.d("Marker", "Did Detect")
    }

    override fun didLose(imageTrackable: ARImageTrackable) {
        Log.d("Marker", "Did Lose")

    }

    override fun didTrack(imageTrackable: ARImageTrackable) {
//        Log.d("Marker", "Did Track")
        // 何で画面上の位置が取得できるのか確認中。
        Log.d("Marker", "[Position] X: " + imageTrackable.world.position.x +
                ", Y: " + imageTrackable.world.position.y +
                ", Z: " + imageTrackable.world.position.z)

        Log.d("Transform",
                "[Transform] m00: " + imageTrackable.world.worldTransform.m00 +
                ", m01: " + imageTrackable.world.worldTransform.m01 +
                ", m02: " + imageTrackable.world.worldTransform.m02 +
                ", m03: " + imageTrackable.world.worldTransform.m03 + "¥n" +
                "[Transform] m10: " + imageTrackable.world.worldTransform.m10 +
                ", m11: " + imageTrackable.world.worldTransform.m11 +
                ", m12: " + imageTrackable.world.worldTransform.m12 +
                ", m13: " + imageTrackable.world.worldTransform.m13)

    }

    private fun addImageTrackable(){

        // 1st Marker。ARImageTrackable をインスタンス化して画像を読み込み
        imageTrackable = ARImageTrackable("Lego")
        imageTrackable.loadFromAsset("lego.jpg")

        // 2nd Marker。
        secondImageTrackable = ARImageTrackable("Panel")
        secondImageTrackable.loadFromAsset("panel.jpg")

        // ARImageTracker のインスタンスを取得して初期化
        val trackableManager = ARImageTracker.getInstance()
        trackableManager.initialise()

        // imageTrackable 2つを ARImageTracker に追加
        trackableManager.addTrackable(imageTrackable)
        trackableManager.addTrackable(secondImageTrackable)
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

    private fun addSecondImageNode(){

        // ARImageNode を画像を指定して初期化
        val imageNode = ARImageNode("cow.png")

        // imageNode のサイズを Trackable のサイズに合わせる
        val textureMaterial = imageNode.material as ARTextureMaterial
        val scale = secondImageTrackable.width / textureMaterial.texture.width
        imageNode.scaleByUniform(scale)

        // imageNode を trackable の world に追加
        secondImageTrackable.world.addChild(imageNode)

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

    private fun addSecondModelNode(){

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
        secondImageTrackable.world.addChild(modelNode)

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

        val secondNodes = secondImageTrackable.world.children
        for (node in secondNodes)
            node.visible = false
    }

    fun clearAllButtonClicked(view: View){
        hideAll()
    }

    fun showImageButtonClicked(view: View){
        hideAll()
        imageTrackable.world.children[0].visible = true
        secondImageTrackable.world.children[0].visible = true
    }

    fun showModelButtonClicked(view: View){
        hideAll()
        imageTrackable.world.children[1].visible = true
        secondImageTrackable.world.children[1].visible = true
    }

    fun showVideoButtonClicked(view: View){
        hideAll()
        videoNode.videoTexture.reset()
        videoNode.videoTexture.start()

        imageTrackable.world.children[2].visible = true
    }

}