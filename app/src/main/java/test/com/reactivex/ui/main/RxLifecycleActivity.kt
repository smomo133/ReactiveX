package test.com.reactivex.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindUntilEvent
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import io.reactivex.rxjava3.core.Observable
import test.com.reactivex.R
import java.util.concurrent.TimeUnit

class RxLifecycleActivity : RxAppCompatActivity() {

    companion object {
        fun newInstance() = RxLifecycleActivity()
        private val TAG = "RxLifecycleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.lifecycle_activity)

        // Specifically bind this until onPause()
        Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { Log.i(TAG, "Unsubscribing subscription from onCreate()") }
            .bindUntilEvent(this, Lifecycle.Event.ON_PAUSE)
            .subscribe { num -> Log.i(TAG, "Started in onCreate(), running until onPause(): " + num) }
    }

    override fun onStart() {
        super.onStart()

        // Using automatic unsubscription, this should determine that the correct time to
        // unsubscribe is onStop (the opposite of onStart).
        Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { Log.i(TAG, "Unsubscribing subscription from onStart()") }
            .bindToLifecycle(this)
            .subscribe { num -> Log.i(TAG, "Started in onStart(), running until in onStop(): " + num) }
    }

    override fun onResume() {
        super.onResume()

        Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { Log.i(TAG, "Unsubscribing subscription from onResume()") }
            .bindUntilEvent(this, Lifecycle.Event.ON_DESTROY)
            .subscribe { num -> Log.i(TAG, "Started in onResume(), running until in onDestroy(): " + num) }
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }
}