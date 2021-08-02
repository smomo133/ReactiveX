package test.com.reactivex.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.jakewharton.rxbinding4.viewpager2.pageScrollEvents
import com.jakewharton.rxbinding4.viewpager2.pageSelections
import com.jakewharton.rxbinding4.widget.itemSelections
import io.reactivex.rxjava3.disposables.CompositeDisposable
import test.com.reactivex.R
import test.com.reactivex.data.ViewpagerAdapter

class RxBindingActivity :AppCompatActivity(){
    private lateinit var viewpager:ViewPager2
    private var disposables = CompositeDisposable()
    private val bgColors: MutableList<Int> = mutableListOf(
            android.R.color.white,
            android.R.color.holo_blue_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rxbinding_activity)
        viewpager = findViewById(R.id.view_pager)
        viewpager.adapter = ViewpagerAdapter(bgColors)

        val viewpagerDisposable = viewpager.pageSelections()
                .subscribe {
                    Log.d(TAG, "pageSelected page = $it")
                }
        disposables.add(viewpagerDisposable)

        val viewpagerDisposable2 = viewpager.pageScrollEvents()
                .subscribe {
                    Log.d(TAG, "PageScrollEvent position = ${it.position}, positionOffset = ${it.positionOffset}")
                }
        disposables.add(viewpagerDisposable2)

        val spinner = findViewById<Spinner>(R.id.orientation_spinner)
        val adapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item,
                    arrayOf(HORIZONTAL, VERTICAL))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val orientation = viewpager.orientation
        spinner.setSelection(orientation)
        val spinnerDisposable = spinner.itemSelections()
            .subscribe {
                viewpager.orientation = it
            }
        disposables.add(spinnerDisposable)
    }

    private fun orientationToString(orientation: Int): String {
        return when (orientation) {
            ViewPager2.ORIENTATION_HORIZONTAL -> HORIZONTAL
            ViewPager2.ORIENTATION_VERTICAL -> VERTICAL
            else -> throw IllegalArgumentException("Orientation $orientation doesn't exist")
        }
    }

    internal fun stringToOrientation(string: String): Int {
        return when (string) {
            HORIZONTAL -> ViewPager2.ORIENTATION_HORIZONTAL
            VERTICAL -> ViewPager2.ORIENTATION_VERTICAL
            else -> throw IllegalArgumentException("Orientation $string doesn't exist")
        }
    }

    override fun onStop() {
        super.onStop()
        if(disposables.size() > 0){
            disposables.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object{
        private val TAG = RxBindingActivity::class.java.simpleName
        private const val HORIZONTAL = "horizontal"
        private const val VERTICAL = "vertical"
    }
}