package com.mrbaikal.fallofrisk.ui.fourth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrbaikal.fallofrisk.R

@Composable
fun FourthScreen(
    riskState: Int? = null,
    onCalculate: ((Double, Double, Int, Double, Double, Double) -> Unit)? = null
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val errorText = stringResource(id = R.string.input_error_text)
    val resultOptions = stringArrayResource(id = R.array.result_options)
    var dialogState by remember { mutableStateOf(false) }

    var foamState by remember { mutableStateOf("") }
    var tandemState by remember { mutableStateOf("") }
    var reachState by remember { mutableStateOf("") }
    var goState by remember { mutableStateOf("") }
    var eyesOpenState by remember { mutableStateOf("") }
    var eyesClosedState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = foamState,
                onValueChange = { foamState = it },
                label = {
                    Text(stringResource(id = R.string.foam_romberg_hint))
                },
                keyboardActions = KeyboardActions.Default
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = tandemState,
                onValueChange = { tandemState = it },
                label = {
                    Text(stringResource(id = R.string.tandem_hint))
                },
                keyboardActions = KeyboardActions.Default
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = reachState,
                onValueChange = { reachState = it },
                label = {
                    Text(stringResource(id = R.string.reaching_hint))
                },
                keyboardActions = KeyboardActions.Default
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = goState,
                onValueChange = { goState = it },
                label = {
                    Text(stringResource(id = R.string.go_hint))
                },
                keyboardActions = KeyboardActions.Default
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = eyesOpenState,
                onValueChange = { eyesOpenState = it },
                label = {
                    Text(stringResource(id = R.string.eyes_open_hint))
                },
                keyboardActions = KeyboardActions.Default
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = eyesClosedState,
                onValueChange = { eyesClosedState = it },
                label = {
                    Text(stringResource(id = R.string.eyes_closed_hint))
                },
                keyboardActions = KeyboardActions.Default
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 16.dp),
                onClick = {
                    try {
                        onCalculate?.invoke(
                            foamState.toDouble(),
                            tandemState.toDouble(),
                            reachState.toInt(),
                            goState.toDouble(),
                            eyesOpenState.toDouble(),
                            eyesClosedState.toDouble()
                        )
                        dialogState = true
                    } catch (e: Exception) {
                        Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(
                    fontSize = 16.sp,
                    text = stringResource(id = R.string.calculate_text)
                )
            }
        }

        if (dialogState && riskState != null) {
            AlertDialog(onDismissRequest = { dialogState = false },
                title = {
                    Text(stringResource(id = R.string.result_text))
                },
                text = {
                    Text(resultOptions[riskState])
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            dialogState = false
                        }
                    ) {
                        Text(stringResource(id = R.string.close_text))
                    }
                }
            )
        }
    }
}