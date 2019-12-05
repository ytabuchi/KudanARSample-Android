package jp.ytabuchi.kudanarsample

import android.os.Bundle
import eu.kudan.kudan.*
import android.view.View
import eu.kudan.kudan.ARVideoNode
import eu.kudan.kudan.ARVideoTexture

class VideoActivity : ARActivity() {

    private lateinit var imageTrackable: ARImageTrackable
    private lateinit var alphaVideoNode: ARAlphaVideoNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video)


    }

    override fun setup() {

        addImageTrackable()

        addAlphaVideoNode()
//        addAlphaVideoNode()

    }

    private fun addImageTrackable(){

        // 1st Marker。ARImageTrackable をインスタンス化して画像を読み込み
        imageTrackable = ARImageTrackable("Lego")
        imageTrackable.loadFromAsset("lego.jpg")

        // ARImageTracker のインスタンスを取得して初期化
        val trackableManager = ARImageTracker.getInstance()
        trackableManager.initialise()

        // imageTrackable を ARImageTracker に追加
        trackableManager.addTrackable(imageTrackable)
    }

    private fun addAlphaVideoNode(){

        // ARVideoTexture を mp4 ファイルで初期化
        val videoTexture = ARVideoTexture()
        videoTexture.loadFromAsset("kaboom.mp4")

        // ARVideoTexture で ARVideoNode をインスタンス化
        alphaVideoNode = ARAlphaVideoNode(videoTexture)

        // videoNode のサイズを Trackable のサイズに合わせる
        val scale = imageTrackable.width / videoTexture.width
        alphaVideoNode.scaleByUniform(scale * 3)

        // videoNode に角度をつける
        alphaVideoNode.rotateByRadians(45f, 90f, 90f, 0f)

        // videoNode を trackable の world に追加
        imageTrackable.world.addChild(alphaVideoNode)

        // 初期状態で表示
        alphaVideoNode.visible = true
    }

    // 全ての Node を非表示
//    private fun hideAll(){
//        val nodes = imageTrackable.world.children
//        for (node in nodes)
//            node.visible = false
//    }

//    fun clearAllButtonClicked(view: View){
//        hideAll()
//    }
    
}