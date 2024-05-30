package checks.environment.sobriety

import android.app.Activity
import android.content.Context
import android.hardware.camera2.CameraManager

class TorchFreeCheckSample : Activity() {
    val IS_ENABLED = true
    val IS_NOT_ENABLED = false

    private fun check() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        cameraManager.setTorchMode(cameraId, true) // Noncompliant
        cameraManager.setTorchMode(cameraId, false)

        val innerVariable = true
        val innerVariableNot = false

        cameraManager.setTorchMode(cameraId, innerVariable) // Noncompliant
        cameraManager.setTorchMode(cameraId, innerVariableNot)

        cameraManager.setTorchMode(cameraId, IS_ENABLED) // Noncompliant
        cameraManager.setTorchMode(cameraId, IS_NOT_ENABLED)
    }
}