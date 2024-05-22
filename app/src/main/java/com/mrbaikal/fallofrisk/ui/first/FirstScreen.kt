package com.mrbaikal.fallofrisk.ui.first

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrbaikal.fallofrisk.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

@Composable
fun FirstScreen(
    onNext: ((Int, Double) -> Unit)? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val errorText = stringResource(id = R.string.input_error_text)

    var lengthState by remember { mutableStateOf("") }
    var weightState by remember { mutableStateOf("") }
    var vkiState by remember { mutableDoubleStateOf(0.0) }
    var buttonState by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 16.sp,
                text = stringResource(R.string.app_description)
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = lengthState,
                onValueChange = { lengthState = it },
                label = {
                    Text(stringResource(id = R.string.length_hint))
                },
                keyboardActions = KeyboardActions.Default
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = weightState,
                onValueChange = { weightState = it },
                label = {
                    Text(stringResource(id = R.string.weigth_hint))
                },
                keyboardActions = KeyboardActions.Default
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = (vkiState != 0.0),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.vki_result_text, vkiState)
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 16.dp),
                enabled = buttonState,
                onClick = {
                    buttonState = false
                    coroutineScope.launch {
                        try {
                            val lengthInMeter = (lengthState.toDouble() / 100.00)
                            val vki = weightState.toDouble() / lengthInMeter.pow(2.00)
                            vkiState = vki
                            delay(1000)
                            onNext?.invoke(lengthState.toInt(), vkiState)
                        } catch (e: Exception) {
                            Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            ) {
                Text(
                    fontSize = 16.sp,
                    text = stringResource(id = R.string.next_text)
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    FirstScreen()
}