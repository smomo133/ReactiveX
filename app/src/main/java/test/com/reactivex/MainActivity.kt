package test.com.reactivex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import test.com.reactivex.data.Example
import test.com.reactivex.data.exampleList
import test.com.reactivex.ui.main.RxLifecycleActivity
import test.com.reactivex.ui.main.RxPermissionFragment
import test.com.reactivex.ui.main.SamplesAdapter
import test.com.reactivex.ui.main.SubjectFragment

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
            0 -> startLifeCycleActivity()
            1 -> startFragment(RxPermissionFragment())
            2 -> startFragment(SubjectFragment())
        }
    }

    private fun startLifeCycleActivity(){
        val intent = Intent(this, RxLifecycleActivity()::class.java)
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