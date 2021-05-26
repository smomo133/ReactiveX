package test.com.reactivex.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource {
    private val initExampleList = exampleList()
    private val exampleLiveData = MutableLiveData(initExampleList)

    fun getSampleId(id:Int):Example?{
        exampleLiveData.value?.let {
            return it.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getSampleList():LiveData<List<Example>>{
        return exampleLiveData
    }

    companion object{
        private var INSTANCE:DataSource? = null

        fun getDataSource():DataSource{
            return synchronized(DataSource::class){
                val newInstance = INSTANCE ?: DataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}