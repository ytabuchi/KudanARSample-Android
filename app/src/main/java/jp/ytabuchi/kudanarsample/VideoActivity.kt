package jp.ytabuchi.kudanarsample

import android.os.Bundle
import android.util.Log
import eu.kudan.kudan.*
import android.view.View
import android.widget.TextView
import eu.kudan.kudan.ARVideoNode
import eu.kudan.kudan.ARVideoTexture

class VideoActivity : ARActivity(), ARImageTrackableListener {

    private lateinit var imageTrackable: ARImageTrackable
    private lateinit var alphaVideoNode: ARAlphaVideoNode
    private lateinit var detectText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video)

        detectText = findViewById<TextView>(R.id.detectText)
    }

    override fun setup() {

        addImageTrackable()

        addAlphaVideoNode()
//        addAlphaVideoNode()

        // Listnerを追加
        imageTrackable.addListener(this)
    }

    // ARImageTrackableListener インターフェースの実装。これらはトラッキングイベントが発生すると呼ばれます。
    override fun didDetect(imageTrackable: ARImageTrackable) {
        Log.d("Marker", "Did Detect")
        detectText.text = "Did Detect"
    }

    override fun didLose(imageTrackable: ARImageTrackable) {
        Log.d("Marker", "Did Lose")
        detectText.text = "Did Lose"

        //TODO: Show video in full screen.

    }

    override fun didTrack(imageTrackable: ARImageTrackable) {
        Log.d("Marker", "Did Track")
        detectText.text = "Tracking"
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