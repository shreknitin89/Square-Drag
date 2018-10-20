package com.plastic.ndasari.squaredrag

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.plastic.ndasari.squaredrag.ui.square.SquareFragment


/**
 * Created by ndasari on 19 Oct 2018
 */
class SquareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.square_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SquareFragment.newInstance())
                .commitNow()
        }
    }
}
