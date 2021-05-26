package test.com.reactivex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import test.com.reactivex.R

class SubjectFragment : Fragment() {
    private lateinit var result_view:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
        = inflater.inflate(R.layout.subject_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result_view = view.findViewById<TextView>(R.id.tv_result)

        asyncSubject()
    }

    private fun asyncSubject(){
        val observable = Observable.just(1,2,3,4,5,6,7,8,9,10)
        val asyncSubject = AsyncSubject.create<Int>()
        observable.subscribe(asyncSubject)
        asyncSubject.subscribe{
            println("Subscribe_1st : $it")
            result_view.append("Subscribe_1st : $it \n")
        }
        asyncSubject.subscribe{
            println("Subscribe_2st : $it")
            result_view.append("Subscribe_2st : $it \n")
        }
    }

    private fun publishSubject(){
        val publishSubject = PublishSubject.create<String>()
        publishSubject.subscribe {
            println("Subscribe_1st = $it")
        }
        publishSubject.onNext("AA")
        publishSubject.onNext("BB")
        publishSubject.subscribe {
            println("Subscribe_2st = $it")
        }
        publishSubject.onNext("CC")
        publishSubject.subscribe {
            println("Subscribe_3st = $it")
        }
        publishSubject.onNext("DD")
        publishSubject.onComplete()
    }

    fun main(){
        val behaviorSubject = BehaviorSubject.createDefault<String>("AA")
        behaviorSubject.subscribe {
            println("Subscribe_1st = $it")
        }
        behaviorSubject.onNext("BB")
        behaviorSubject.onNext("CC")
        behaviorSubject.subscribe {
            println("Subscribe_2st = $it")
        }
        behaviorSubject.onNext("DD")
        behaviorSubject.onComplete()
    }

    private fun replaySubject(){
        val replaySubject = ReplaySubject.create<String>()
        replaySubject.subscribe {
            println("Subscribe_1st = $it")
        }
        replaySubject.onNext("AA")
        replaySubject.onNext("BB")
        replaySubject.subscribe {
            println("Subscribe_2st = $it")
        }
        replaySubject.onNext("CC")
        replaySubject.onComplete()
    }
}