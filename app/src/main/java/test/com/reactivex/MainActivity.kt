package test.com.reactivex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import test.com.reactivex.data.Example
import test.com.reactivex.data.exampleList
import test.com.reactivex.ui.main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val samplesAdapter = SamplesAdapter{sample -> adapterOnClick(sample) }
        val recyclerView:RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = samplesAdapter
        samplesAdapter.submitList(exampleList())
    }

    private fun adapterOnClick(sample:Example){
        when(sample.id){
            0 -> startAct(RxLifecycleActivity()::class.java.name)
            1 -> startFragment(RxPermissionFragment())
            2 -> startFragment(SubjectFragment())
            3 -> startAct(RxBindingActivity()::class.java.name)
        }
    }

    private fun startAct(activity:String){
        val intent = Intent(Intent.ACTION_VIEW).setClassName(this.packageName, activity)
        startActivity(intent)
    }

    private fun startFragment(fragment:Fragment){
        val tag = fragment.javaClass.simpleName
        supportFragmentManager
        .beginTransaction()
        .addToBackStack(tag)
        .replace(android.R.id.content, fragment, tag)
        .commit()
    }
}