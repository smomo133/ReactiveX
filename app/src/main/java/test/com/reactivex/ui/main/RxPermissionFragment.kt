package test.com.reactivex.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.ImageFormat
import android.graphics.Point
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.hardware.camera2.params.SessionConfiguration
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.Permission
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import test.com.reactivex.R
import java.io.IOException
import java.lang.RuntimeException
import java.util.*


class RxPermissionFragment : Fragment() {
    private var disposable: Disposable? = null
    private lateinit var enableCamera: Button
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
        enableCamera = view.findViewById(R.id.enable_camera)


        val rxPermissions = RxPermissions(this)
        disposable = enableCamera.clicks()
            .compose(rxPermissions.ensureEach(Manifest.permission.CAMERA))
            .subscribe(
                Consumer<Permission>() {
                    if (it.granted) {
                        try {
                            //openCamera(textureView!!.width, textureView!!.height)
                            Toast.makeText(
                             mContext,
                                "permission result = " + it.toString(), Toast.LENGTH_SHORT
                            ).show()
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
                    Log.e(TAG,"onError", it)
                },
                Action {
                    Log.i(TAG,"onComplete")
                }
            )
    }

    override fun onDestroy() {
        if(disposable != null && disposable!!.isDisposed){
            disposable!!.dispose()
        }
        super.onDestroy()
    }
}