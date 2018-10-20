package com.plastic.ndasari.squaredrag.ui.square

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.util.TimeFormatException
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import com.plastic.ndasari.squaredrag.R
import com.plastic.ndasari.squaredrag.customview.SquareView
import kotlinx.android.synthetic.main.square_fragment.*


/**
 * Created by ndasari on 19 Oct 2018
 */
class SquareFragment : Fragment(), View.OnDragListener, View.OnLongClickListener {

    companion object {
        fun newInstance() = SquareFragment()
        const val TAG: String = "TAG"
    }

    private lateinit var viewModel: SquareViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.square_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SquareViewModel::class.java)

        initializeView()
        rotateView()

        if (hasInternet()) {
            try {
                viewModel.getDateData().observe(this, Observer { liveData ->
                    val seconds = liveData?.secondsData() ?: getString(R.string.text_place_holder)
                    timerView.text = seconds
                })
            } catch (ex: TimeFormatException) {
                Toast.makeText(this.context, "Incorrect Data Format", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this.context, "Internet Not Available", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        viewModel.stopDataFetch()
        super.onPause()
    }

    private fun rotateView() {
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.rotate_once_every_second_animation)
        animation.fillAfter = true
        timerView.animation = animation
    }

    private fun initializeView() {
        timerView.tag = TAG
        timerView.setOnLongClickListener(this)
        bottomViewGroup.setOnDragListener(this)
    }

    private fun hasInternet(): Boolean {
        val connectivityManager = this.context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = if (connectivityManager.activeNetworkInfo != null) connectivityManager.activeNetworkInfo else null
        return netInfo != null && netInfo.isConnected
    }

    override fun onDrag(v: View, event: DragEvent): Boolean {
        val action = event.action
        // Handles each of the expected events
        when (action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                return dragStartAnimation(event, v)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                return dragStartAnimation(event, v)
            }
            DragEvent.ACTION_DRAG_LOCATION ->
                return true
            DragEvent.ACTION_DRAG_EXITED -> {
                return dragExitAnimation(v)
            }
            DragEvent.ACTION_DROP -> {
                return dragDropEvent(event, v)
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                return true
            }

            // An unknown action type was received.
            else -> Log.e("Unknown Action", "can not perform action")
        }
        return false
    }

    private fun dragDropEvent(event: DragEvent, v: View): Boolean {
        val item: ClipData.Item = event.clipData.getItemAt(0)
        val data = item.text
        Log.i("Data When Dropped", data.toString())

        val squareView = event.localState as? SquareView
        squareView?.clearAnimation()

        val owner = squareView?.parent as ViewGroup?
        owner?.removeView(squareView)

        (v as? LinearLayout)?.addView(squareView)

        squareView?.visibility = View.VISIBLE
        squareView?.gravity = Gravity.CENTER_VERTICAL
        rotateView()

        v.invalidate()
        return true
    }

    private fun dragExitAnimation(v: View): Boolean {
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT, 1.0.toFloat()
        )

        (v as? LinearLayout)?.layoutParams = param
        val slideAnimation =
            AnimationUtils.loadAnimation(this@SquareFragment.context, R.anim.slide_down_animation)
        v.startAnimation(slideAnimation)

        v.invalidate()
        return true
    }

    private fun dragStartAnimation(event: DragEvent, v: View): Boolean {
        return if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 3.0.toFloat()
            )

            (v as? LinearLayout)?.layoutParams = param
            val slideAnimation =
                AnimationUtils.loadAnimation(this@SquareFragment.context, R.anim.slide_up_animation)
            v.startAnimation(slideAnimation)
            v.invalidate()
            true
        } else {
            false
        }
    }

    @Suppress("DEPRECATION")
    override fun onLongClick(v: View): Boolean {
        val item = ClipData.Item(v.tag as? CharSequence)

        val dragData = ClipData(
            v.tag as? CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item
        )

        val myShadow = View.DragShadowBuilder(v)

        v.startDrag(
            dragData,
            myShadow,
            v,
            0
        )

        v.visibility = View.INVISIBLE
        return true
    }
}
