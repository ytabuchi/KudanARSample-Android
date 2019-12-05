package jp.ytabuchi.kudanarsample

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import eu.kudan.kudan.*
//import sun.jvm.hotspot.utilities.IntArray


class VideoActivity : ARActivity(), ARImageTrackableListener {

    private lateinit var imageTrackable: ARImageTrackable
    private lateinit var videoNode: ARVideoNode
    private lateinit var detectText: TextView
    private var screenW: Int = 0
    private var screenH: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video)

        detectText = findViewById<TextView>(R.id.detectText)

        // Window サイズを取得
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val disp = wm.defaultDisplay
        val size = Point()
        disp.getSize(size)
        screenW = size.x
        screenH = size.y

    }


    override fun setup() {

        addImageTrackable()

        addVideoNode()
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
//        Log.d("Marker", "Did Lose")
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

    private fun addVideoNode(){

        // ARVideoTexture を mp4 ファイルで初期化
        val videoTexture = ARVideoTexture()
        videoTexture.loadFromAsset("water-and-bubbles.mp4")

        // ARVideoTexture で ARVideoNode をインスタンス化
        videoNode = ARVideoNode(videoTexture)

        // videoNode のサイズを Trackable のサイズに合わせる
        val scale = imageTrackable.width / videoTexture.width
        videoNode.scaleByUniform(scale)

        // videoNode を trackable の world に追加
        imageTrackable.world.addChild(videoNode)

        // 初期状態で表示
        videoNode.visible = true
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