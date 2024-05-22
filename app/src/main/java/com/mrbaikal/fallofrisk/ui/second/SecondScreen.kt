package com.mrbaikal.fallofrisk.ui.second

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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    onNext: ((Int, Int, Int) -> Unit)? = null
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val errorText = stringResource(id = R.string.input_error_text)

    var imbalanceStateExpanded by remember { mutableStateOf(false) }
    val imbalanceOptions = stringArrayResource(id = R.array.boolean_options)
    val (imbalanceSelected, onImbalanceSelected) = remember { mutableStateOf(imbalanceOptions[1]) }

    var imbalanceValueStateExpanded by remember { mutableStateOf(false) }
    val imbalanceValueOptions = stringArrayResource(id = R.array.numeric_options)
    val (imbalanceValueSelected, onImbalanceValueSelected) = remember {
        mutableStateOf(imbalanceValueOptions[4])
    }

    var imbalanceTimeState by remember { mutableStateOf("36") }
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
            ExposedDropdownMenuBox(
                modifier = Modifier.padding(top = 16.dp),
                expanded = imbalanceStateExpanded,
                onExpandedChange = {
                    imbalanceStateExpanded = !imbalanceStateExpanded
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = imbalanceSelected,
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.imbalance_state_hint)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = imbalanceStateExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = imbalanceStateExpanded,
                    onDismissRequest = {
                        imbalanceStateExpanded = false
                    }
                ) {
                    imbalanceOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                onImbalanceSelected(option)
                                imbalanceStateExpanded = false
                            },
                            text = {
                                Text(text = option)
                            }
                        )
                    }
                }
            }
            ExposedDropdownMenuBox(
                modifier = Modifier.padding(top = 16.dp),
                expanded = imbalanceValueStateExpanded,
                onExpandedChange = {
                    imbalanceValueStateExpanded = !imbalanceValueStateExpanded
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = imbalanceValueSelected,
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.imbalance_hint)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = imbalanceValueStateExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = imbalanceValueStateExpanded,
                    onDismissRequest = {
                        imbalanceValueStateExpanded = false
                    }
                ) {
                    imbalanceValueOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                onImbalanceValueSelected(option)
                                imbalanceValueStateExpanded = false
                            },
                            text = {
                                Text(text = option)
                            }
                        )
                    }
                }
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = imbalanceTimeState,
                onValueChange = { imbalanceTimeState = it },
                label = {
                    Text(stringResource(id = R.string.imbalance_time_hint))
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
                enabled = buttonState,
                onClick = {
                    try {
                        buttonState = false
                        onNext?.invoke(
                            imbalanceOptions.indexOf(imbalanceSelected),
                            imbalanceValueOptions.indexOf(imbalanceValueSelected),
                            imbalanceTimeState.toInt(),
                        )
                    } catch (e: Exception) {
                        Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()
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