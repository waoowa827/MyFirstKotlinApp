package com.example.myfirstkotlinapp

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "MainActivity"
    private var start: Button? = null
    private var stop: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start = findViewById<View>(R.id.startButton) as Button

        // assigning ID of stopButton
        // to the object stop
        stop = findViewById<View>(R.id.stopButton) as Button

        // declaring listeners for the
        // buttons to make them respond
        // correctly according to the process
        start!!.setOnClickListener(this)
        stop!!.setOnClickListener(this)


        val intent = Intent(this, MyFirstIntentService::class.java)
        Log.i(TAG, " Service Invoked")
        startService(intent)

    }

    fun isServiceRunning(serviceClass: Class<*>, context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = activityManager.getRunningServices(Integer.MAX_VALUE)

        for (i in services.indices) {
            if (serviceClass.name == services[i].service.className) {
                return true
            }
        }
        return false
    }

    override fun onClick(view: View) {


        // process to be performed
        // if start button is clicked
        if (view === start) {

            val isMyServiceRunning = isServiceRunning(MyFirstIntentService::class.java, applicationContext)
            if (isMyServiceRunning) {
                // the service is running
                Log.i(TAG, " Service running!!!!")
            } else {
                Log.i(TAG, " Service started!!!!")
                startService(Intent(this, MyFirstIntentService::class.java))
                // the service is not running
            }
            // starting the service

        }

        // process to be performed
        // if stop button is clicked
        else if (view === stop) {

            // stopping the service
            stopService(Intent(this, MyFirstIntentService::class.java))
        }
    }
}