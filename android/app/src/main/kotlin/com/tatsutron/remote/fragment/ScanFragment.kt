package com.tatsutron.remote.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.tatsutron.remote.MainActivity
import com.tatsutron.remote.Permissions
import com.tatsutron.remote.R
import com.tatsutron.remote.util.Dialog
import com.tatsutron.remote.util.Navigator
import com.tatsutron.remote.util.Persistence
import kotlinx.android.synthetic.main.fragment_scan.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanFragment : BaseFragment() {

    private lateinit var cameraExecutor: ExecutorService
    private var processingBarcode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_scan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (hasPermission()) {
            startCamera()
        } else {
            requestPermissions(
                arrayOf(Permissions.CAMERA.id),
                Permissions.CAMERA.requestCode,
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        if (requestCode == Permissions.CAMERA.requestCode) {
            if (hasPermission()) {
                startCamera()
            } else {
                val context = requireContext()
                Dialog.info(
                    context = context,
                    message = context.getString(
                        Permissions.CAMERA.noPermissionStringId,
                    ),
                    ok = {
                        (activity as? MainActivity)?.onBackPressed()
                    },
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        processingBarcode = false
    }

    override fun onDestroy() {
        cameraExecutor.shutdown()
        super.onDestroy()
    }

    private fun startCamera() {
        val context = requireContext()
        val future = ProcessCameraProvider.getInstance(context)
        future.addListener(
            {
                val provider: ProcessCameraProvider = future.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(preview.surfaceProvider)
                }
                val imageAnalysis = ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(
                            cameraExecutor,
                            BarcodeAnalyzer(
                                context = context,
                                listener = { data ->
                                    if (!processingBarcode) {
                                        processingBarcode = true
                                        handleResult(data)
                                    }
                                },
                            ),
                        )
                    }
                try {
                    provider.unbindAll()
                    provider.bindToLifecycle(
                        this,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageAnalysis,
                    )
                } catch (e: Exception) {
                    Dialog.error(context, e)
                }
            },
            ContextCompat.getMainExecutor(context),
        )
    }

    private fun hasPermission() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Permissions.CAMERA.id,
        ) == PackageManager.PERMISSION_GRANTED

    private fun handleResult(data: String) {
        Persistence.getGameBySha1(data)
            ?.let { game ->
                Navigator.showLoadingScreen()
                game.play(
                    requireActivity(),
                    callback = {
                        Navigator.hideLoadingScreen()
                        Handler(Looper.getMainLooper()).postDelayed({
                            (activity as? MainActivity)?.onBackPressed()
                        }, 100)
                    },
                )
            }
            ?: run {
                processingBarcode = false
            }
    }
}