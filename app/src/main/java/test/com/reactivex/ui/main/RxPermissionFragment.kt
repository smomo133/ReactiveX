package test.com.reactivex.ui.main

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.Permission
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import test.com.reactivex.R
import java.io.IOException


class RxPermissionFragment : Fragment() {
    private var textureView:TextureView? = null
    private var disposable:Disposable? = null
    private lateinit var enableCamera:Button
    private lateinit var mContext: Context

    companion object {
        fun newInstance() = RxPermissionFragment()
        private val TAG = "RxPermissionFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.permission_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textureView = view.findViewById(R.id.textureView)
        enableCamera = view.findViewById(R.id.enable_camera)


        val rxPermissions = RxPermissions(this)
        disposable = enableCamera.clicks()
            .compose(rxPermissions.ensureEach(Manifest.permission.CAMERA))
            .subscribe(
                Consumer<Permission>() {
                    Log.d(TAG, "Permission result = " + it.toString())
                    if (it.granted) {
                        try {

                        } catch (e: IOException) {
                            Log.e(TAG, "Error while trying to display the camera preview", e)
                        }
                    } else if (it.shouldShowRequestPermissionRationale) {
                        Toast.makeText(
                            mContext,
                            "Denied permission without ask never again", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            mContext,
                            "Permission dened, can't enable the camera", Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Consumer<Throwable>() {

                },
                Action {

                }
            )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        //
    }

    override fun onPause() {
        //
        super.onPause()
    }
}