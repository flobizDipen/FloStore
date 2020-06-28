package com.valorem.flostore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mRunnable = Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        mHandler = Handler()
        mHandler?.postDelayed(mRunnable, 3000)
    }

    override fun onDestroy() {
        mRunnable?.let {
            mHandler?.removeCallbacks(it)
        }

        super.onDestroy()
    }
}