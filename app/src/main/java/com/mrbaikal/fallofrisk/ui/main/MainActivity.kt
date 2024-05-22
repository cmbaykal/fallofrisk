package com.mrbaikal.fallofrisk.ui.main


import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrbaikal.fallofrisk.R
import com.mrbaikal.fallofrisk.base.theme.FallOfRiskTheme
import com.mrbaikal.fallofrisk.ui.first.FirstScreen
import com.mrbaikal.fallofrisk.ui.fourth.FourthScreen
import com.mrbaikal.fallofrisk.ui.second.SecondScreen
import com.mrbaikal.fallofrisk.ui.third.ThirdScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FallOfRiskTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val controller = rememberNavController()
                    val riskState = viewModel.result.collectAsState()

                    NavHost(navController = controller, startDestination = "firstScreen") {
                        composable("firstScreen") {
                            FirstScreen { length, vki ->
                                viewModel.setLengthState(length)
                                viewModel.setVkiState(vki)
                                controller.navigate("secondScreen")
                            }
                        }
                        composable("secondScreen") {
                            SecondScreen { imbalance, value, time ->
                                viewModel.setImbalanceState(imbalance)
                                viewModel.setImbalanceValueState(value)
                                viewModel.setImbalanceTimeState(time)
                                controller.navigate("thirdScreen")
                            }
                        }
                        composable("thirdScreen") {
                            ThirdScreen { hypertension, diabetes ->
                                viewModel.setHyperTensionState(hypertension)
                                viewModel.setDiabetesState(diabetes)
                                controller.navigate("fourthScreen")
                            }
                        }
                        composable("fourthScreen") {
                            FourthScreen(
                                riskState.value
                            ) { foam, tandem, reach, go, open, closed ->
                                val environment = OrtEnvironment.getEnvironment()
                                val session = createORTSession(environment)

                                viewModel.setFoamState(foam)
                                viewModel.setTandemState(tandem)
                                viewModel.setReachState(reach)
                                viewModel.setGoState(go)
                                viewModel.setEyesOpenState(open)
                                viewModel.setEyesClosedState(closed)
                                viewModel.calculateResult(session, environment)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createORTSession(environment: OrtEnvironment): OrtSession {
        val modelBytes = resources.openRawResource(R.raw.model).readBytes()
        return environment.createSession(modelBytes)
    }
}