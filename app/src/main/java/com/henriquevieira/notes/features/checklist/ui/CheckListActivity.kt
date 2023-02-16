package com.henriquevieira.notes.features.checklist.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.button.CustomCircleIconButton
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.commonsui.ds.color_card_green
import com.henriquevieira.notes.R
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.extensions.showToast
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListResult
import com.henriquevieira.notes.features.checklist.viewmodel.CheckListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckListActivity : BaseActivity() {
    private val viewModel: CheckListViewModel by viewModels()

    private val showAddItemDialog = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.dispatch(CheckListAction.Init)
        setContent {
            AppTheme {
                ConstraintLayout {
                    val (screenRef, addDialogRef) = createRefs()

                    CheckListScreen(
                        uiState = viewModel.uiState.collectAsState().value,
                        onUiAction = { viewModel.dispatch(action = it) },
                        modifier = Modifier.constrainAs(screenRef) {
                            width = Dimension.matchParent
                            height = Dimension.matchParent
                        }
                    )

                    AddItemDialog(Modifier.constrainAs(addDialogRef) {
                        top.linkTo(parent.top, 24.dp)
                        centerHorizontallyTo(parent)
                    })
                }
            }
        }
    }

    private fun observe() = lifecycleScope.launch {
        viewModel.screen.collect { state ->
            when (state) {
                is CheckListResult.OnClose -> this@CheckListActivity.onBackPressedDispatcher.onBackPressed()
                is CheckListResult.OnOpenAddItem -> showAddItemDialog.value = true
                is CheckListResult.OnError -> showToast(getString(R.string.fetch_error_message))
                is CheckListResult.OnSaveSuccess -> showToast(getString(R.string.save_success_message))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AddItemDialog(modifier: Modifier) {
        if (showAddItemDialog.value) {
            val text = remember { mutableStateOf("") }

            val textFieldColors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                containerColor = Color.White
            )

            ConstraintLayout(
                modifier = modifier.fillMaxWidth(0.8f).fillMaxHeight(0.4f)
                    .background(color = Color.LightGray, shape = RectangleShape)
                    .border(border = BorderStroke(8.dp, Color.DarkGray))
            ) {
                val (titleRef, textFieldRef, confirmButtonRef, cancelButtonRef) = createRefs()
                Text(
                    text = "Add Item",
                    fontSize = 24.sp,
                    modifier = Modifier.constrainAs(titleRef) {
                        top.linkTo(parent.top, 16.dp)
                        centerHorizontallyTo(parent)
                        width = Dimension.wrapContent
                    }
                )

                TextField(
                    modifier = Modifier.constrainAs(textFieldRef) {
                        top.linkTo(titleRef.bottom, 8.dp)
                        centerHorizontallyTo(parent)
                        width = Dimension.wrapContent
                    },
                    value = text.value,
                    colors = textFieldColors,
                    onValueChange = {
                        text.value = it
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.dispatch(CheckListAction.AddItem(text.value))
                        showAddItemDialog.value = false
                    }),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                )

                CustomCircleIconButton(
                    modifier = Modifier.constrainAs(confirmButtonRef) {
                        top.linkTo(textFieldRef.bottom, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(cancelButtonRef.start)
                    },
                    imageVector = Icons.Sharp.Check,
                    imageColor = color_card_green,
                    backgroundColor = Color.White,
                    onClick = {
                        viewModel.dispatch(CheckListAction.AddItem(text.value))
                        showAddItemDialog.value = false
                    }
                )

                CustomCircleIconButton(
                    modifier = Modifier.constrainAs(cancelButtonRef) {
                        top.linkTo(textFieldRef.bottom, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                        start.linkTo(confirmButtonRef.end)
                        end.linkTo(parent.end)
                    },
                    imageVector = Icons.Sharp.Close,
                    imageColor = Color.Red,
                    backgroundColor = Color.White,
                    onClick = { showAddItemDialog.value = false }
                )
            }
        }
    }
}