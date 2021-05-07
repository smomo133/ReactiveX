package test.com.reactivex.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource {
    private val initSampleList = sampleList()
    private val sampleLiveData = MutableLiveData(initSampleList)

    fun getSampleId(id:Int):Sample?{
        sampleLiveData.value?.let {
            return it.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getSampleList():LiveData<List<Sample>>{
        return sampleLiveData
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