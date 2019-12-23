package jp.ytabuchi.kudanarsample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import eu.kudan.kudan.*

class TrackingActivity : ARActivity(), ARImageTrackableListener {

    private lateinit var imageTrackable: ARImageTrackable
    private lateinit var detectText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tracking)

        detectText = findViewById(R.id.detectText)
    }

    override fun setup() {
        addImageTrackable()

        // Listnerを追加
        imageTrackable.addListener(this)
    }

    // ARImageTrackableListener インターフェースの実装。これらはトラッキングイベントが発生すると呼ばれます。
    override fun didDetect(imageTrackable: ARImageTrackable) {
//        Log.d("Marker", "Did Detect")
        detectText.text = "Did Detect"
    }

    override fun didLose(imageTrackable: ARImageTrackable) {
//        Log.d("Marker", "Did Lose")
        detectText.text = "Did Lose"
    }

    override fun didTrack(imageTrackable: ARImageTrackable) {
        Log.d("Marker", "Did Track")
        detectText.text = "Tracking"
    }

    private fun addImageTrackable(){
        imageTrackable = ARImageTrackable("Lego")
        imageTrackable.loadFromAsset("lego.jpg")

        val trackableManager = ARImageTracker.getInstance()
        trackableManager.initialise()

        trackableManager.addTrackable(imageTrackable)
    }
}