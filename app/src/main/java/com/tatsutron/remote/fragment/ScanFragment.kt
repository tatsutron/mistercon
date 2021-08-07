package com.tatsutron.remote.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tatsutron.remote.BarcodeAnalyzer
import com.tatsutron.remote.Dialog
import com.tatsutron.remote.Permissions
import com.tatsutron.remote.R
import kotlinx.android.synthetic.main.fragment_scan.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
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
        progressBar = view.findViewById(R.id.progress_bar)
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
                // TODO Fail gracefully if no permission
            }
        }
    }

    override fun onResume() {
        super.onResume()
        processingBarcode = false
        progressBar.visibility = View.GONE
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
                                    processingBarcode = true
                                    progressBar.visibility = View.VISIBLE
                                    handleResult(data)
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
        // TODO Try to look up game
        progressBar.visibility = View.GONE
    }
}