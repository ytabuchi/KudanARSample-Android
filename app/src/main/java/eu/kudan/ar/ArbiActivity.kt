package eu.kudan.ar

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import eu.kudan.kudan.ARActivity
import eu.kudan.kudan.ARGyroPlaceManager
import eu.kudan.kudan.ARArbiTrack
import eu.kudan.kudan.ARImageNode
import android.support.v4.view.GestureDetectorCompat
import android.view.View
import android.widget.Button


class ArbiActivity : ARActivity() {

    private lateinit var trackingNode: ARImageNode


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_arbi)
    }


    override fun setup() {
        super.setup()

        addTrackingNode()
        setupArbiTrack()
    }


    private fun addTrackingNode() {

        // トラッキング(表示)するノードを用意
        trackingNode = ARImageNode("CowTracking.png")

        // ノードの画像を正しい向きにするために回転
        trackingNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f)
        trackingNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f)
        trackingNode.rotateByDegrees(-90.0f, 0.0f, 0.0f, 1.0f)

    }

    private fun setupArbiTrack() {

        // ArbiTrack を初期化
        val arbiTrack = ARArbiTrack.getInstance()
        arbiTrack.initialise()

        // ジャイロマネージャーを初期化
        val gyroPlaceManager = ARGyroPlaceManager.getInstance()
        gyroPlaceManager.initialise()


        // ターゲットとして使うノードを用意
        val targetNode = ARImageNode("CowTarget.png")

        // デバイスのジャイロでノードが動くようにノードを　ARGyroPlaceManager に追加
        gyroPlaceManager.world.addChild(targetNode);

        // ノードの画像を正しい向きにするために回転し、サイズを調整
        targetNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f)
        targetNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f)
        targetNode.rotateByDegrees(-90.0f, 0.0f, 0.0f, 1.0f)
        targetNode.scaleByUniform(0.3f);

        // ARArbiTrack の targetNode に指定
        arbiTrack.targetNode = targetNode

        // ARArbiTracker の world に trackingNode を追加
        arbiTrack.world.addChild(trackingNode)

    }


    fun changeTrackingModeButtonClicked(view: View) {

        val arbiTrack = ARArbiTrack.getInstance()
        val b = findViewById<Button>(R.id.changeTrackingModeButton)

        // ARArbitrack のトラッキング状態（配置している状態かどうか）でターゲットの表示／非表示とボタンのテキストを変更
        if (arbiTrack.isTracking) {

            arbiTrack.stop()
            arbiTrack.targetNode.visible = true

            b.text = "Start Tracking"

        } else {

            arbiTrack.start()
            arbiTrack.targetNode.visible = false

            b.text = "Stop Tracking"

        }
    }

}