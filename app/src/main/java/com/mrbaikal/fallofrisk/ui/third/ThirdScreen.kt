package com.mrbaikal.fallofrisk.ui.third

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrbaikal.fallofrisk.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(
    onNext: ((Int, Int) -> Unit)? = null
) {
    val scrollState = rememberScrollState()

    val booleanOptions = stringArrayResource(id = R.array.boolean_options)

    var hypertensionExpanded by remember { mutableStateOf(false) }
    val (hypertensionSelected, onHypertensionSelected) = remember { mutableStateOf(booleanOptions[1]) }

    var diabetesExpanded by remember { mutableStateOf(false) }
    val (diabetesSelected, onDiabetesSelected) = remember { mutableStateOf(booleanOptions[0]) }
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
                expanded = hypertensionExpanded,
                onExpandedChange = {
                    hypertensionExpanded = !hypertensionExpanded
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = hypertensionSelected,
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.hypertension_hint)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = hypertensionExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = hypertensionExpanded,
                    onDismissRequest = {
                        hypertensionExpanded = false
                    }
                ) {
                    booleanOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                onHypertensionSelected(option)
                                hypertensionExpanded = false
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
                expanded = diabetesExpanded,
                onExpandedChange = {
                    diabetesExpanded = !diabetesExpanded
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = diabetesSelected,
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.diabetes_hint)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = diabetesExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = diabetesExpanded,
                    onDismissRequest = {
                        diabetesExpanded = false
                    }
                ) {
                    booleanOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                onDiabetesSelected(option)
                                diabetesExpanded = false
                            },
                            text = {
                                Text(text = option)
                            }
                        )
                    }
                }
            }
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
                    buttonState = false
                    onNext?.invoke(
                        booleanOptions.indexOf(hypertensionSelected),
                        booleanOptions.indexOf(diabetesSelected),
                    )
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