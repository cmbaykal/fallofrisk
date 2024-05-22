package com.mrbaikal.fallofrisk.ui.main


import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mrbaikal.fallofrisk.R
import com.mrbaikal.fallofrisk.base.theme.FallOfRiskTheme
import com.mrbaikal.fallofrisk.ui.first.FirstScreen
import com.mrbaikal.fallofrisk.ui.fourth.FourthScreen
import com.mrbaikal.fallofrisk.ui.second.SecondScreen
import com.mrbaikal.fallofrisk.ui.third.ThirdScreen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
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

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        stringResource(id = R.string.app_name)
                                    )
                                },
                                navigationIcon = {
                                    val backStackState = controller.currentBackStackEntryAsState()
                                    val currentRoute = backStackState.value?.destination?.route

                                    AnimatedVisibility(
                                        visible = currentRoute != null && currentRoute != "firstScreen",
                                        enter = expandHorizontally(),
                                        exit = shrinkHorizontally()
                                    ) {
                                        IconButton(onClick = { controller.navigateUp() }) {
                                            Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
                                        }
                                    }
                                }
                            )
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = controller, startDestination = "firstScreen"
                        ) {
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
    }

    private fun createORTSession(environment: OrtEnvironment): OrtSession {
        val modelBytes = resources.openRawResource(R.raw.model).readBytes()
        return environment.createSession(modelBytes)
    }
}