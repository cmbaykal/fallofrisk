package com.mrbaikal.fallofrisk.ui.main

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.nio.FloatBuffer
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val length = MutableStateFlow(0)
    private val vki = MutableStateFlow(0.0)
    private val imbalance = MutableStateFlow(0)
    private val imbalanceValue = MutableStateFlow(0)
    private val imbalanceTime = MutableStateFlow(0)
    private val hyperTension = MutableStateFlow(0)
    private val diabetes = MutableStateFlow(0)
    private val foam = MutableStateFlow(0.0)
    private val tandem = MutableStateFlow(0.0)
    private val reach = MutableStateFlow(0)
    private val go = MutableStateFlow(0.0)
    private val eyesOpen = MutableStateFlow(0.0)
    private val eyesClosed = MutableStateFlow(0.0)

    private val _result = MutableStateFlow<Int?>(null)
    val result = _result.asStateFlow()

    fun setLengthState(state: Int) {
        length.value = state
    }

    fun setVkiState(state: Double) {
        vki.value = state
    }

    fun setImbalanceState(state: Int) {
        imbalance.value = state
    }

    fun setImbalanceValueState(state: Int) {
        imbalanceValue.value = state
    }

    fun setImbalanceTimeState(state: Int) {
        imbalanceTime.value = state
    }

    fun setHyperTensionState(state: Int) {
        hyperTension.value = state
    }

    fun setDiabetesState(state: Int) {
        diabetes.value = state
    }

    fun setFoamState(state: Double) {
        foam.value = state
    }

    fun setTandemState(state: Double) {
        tandem.value = state
    }

    fun setReachState(state: Int) {
        reach.value = state
    }

    fun setGoState(state: Double) {
        go.value = state
    }

    fun setEyesOpenState(state: Double) {
        eyesOpen.value = state
    }

    fun setEyesClosedState(state: Double) {
        eyesClosed.value = state
    }

    fun calculateResult(ortSession: OrtSession, ortEnvironment: OrtEnvironment) {
        _result.value = null
        try {
            // Get the name of the input node
            val inputName = ortSession.inputNames?.iterator()?.next()
            // Make a FloatBuffer of the inputs
            val floatBufferInputs = FloatBuffer.wrap(
                floatArrayOf(
                    length.value.toFloat(),
                    vki.value.toFloat(),
                    imbalanceValue.value.toFloat(),
                    imbalance.value.toFloat(),
                    imbalanceTime.value.toFloat(),
                    hyperTension.value.toFloat(),
                    diabetes.value.toFloat(),
                    eyesOpen.value.toFloat(),
                    eyesClosed.value.toFloat(),
                    tandem.value.toFloat(),
                    foam.value.toFloat(),
                    go.value.toFloat(),
                    reach.value.toFloat()
                )
            )
            // Create input tensor with floatBufferInputs of shape ( 1 , 1 )
            val inputTensor = OnnxTensor.createTensor(ortEnvironment, floatBufferInputs, longArrayOf(13, 1))
            // Run the model
            val results = ortSession.run(mapOf(inputName to inputTensor))
            // Fetch and return the results
            val output = results[0].value as LongArray

            _result.value = output[0].toInt()
        } catch (e: Exception) {
            println(e.message)
            _result.value = 0
        }
    }
}