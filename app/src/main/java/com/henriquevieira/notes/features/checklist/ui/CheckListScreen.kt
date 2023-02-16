package com.henriquevieira.notes.features.checklist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.button.CustomCircleIconButton
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.commonsui.header.CustomHeader
import com.henriquevieira.notes.R
import com.henriquevieira.notes.data.model.CheckListItem
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListState
import com.henriquevieira.notes.features.note.mvi.NoteAction

@Composable
fun CheckListScreen(
    modifier: Modifier = Modifier,
    uiState: CheckListState,
    onUiAction: (action: CheckListAction) -> Unit
) {
    AppTheme {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
        ) {
            val (headerRef, listRef, addButtonRef, saveButtonRef, progressBarRef) = createRefs()

            CustomHeader(
                title = "Checklist",
                modifier = Modifier.constrainAs(headerRef) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                },
                onCloseButtonClick = { onUiAction.invoke(CheckListAction.CloseButtonClick) }
            )

            ListField(
                uiState = uiState,
                onUiAction = onUiAction,
                modifier = Modifier.constrainAs(listRef) {
                    top.linkTo(headerRef.bottom)
                    bottom.linkTo(saveButtonRef.top)
                    height = Dimension.fillToConstraints
                }
            )

            AddButton(
                onUiEvent = onUiAction,
                modifier = Modifier.constrainAs(addButtonRef) {
                    bottom.linkTo(saveButtonRef.top)
                    end.linkTo(parent.end, 8.dp)
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("SAVE_BUTTON_TAG")
                    .constrainAs(saveButtonRef) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(parent.bottom)
                    },
                content = {
                    Text(stringResource(R.string.save))
                },
                onClick = { onUiAction(CheckListAction.SaveButtonClick) }
            )

            if (uiState.isLoading) {
                CustomProgress(modifier = Modifier.constrainAs(progressBarRef) {
                    centerTo(parent)
                })
            }
        }
    }
}

@Composable
private fun ListField(
    modifier: Modifier,
    uiState: CheckListState,
    onUiAction: (action: CheckListAction) -> Unit,
) {
    LazyColumn(modifier) {
        uiState.itemsList?.let { items ->
            items(items.size) { index ->
                val isChecked = remember { mutableStateOf(uiState.itemsList[index].isChecked) }
                ConstraintLayout(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(5.dp)
                        .background(color = if (isChecked.value) Color.LightGray else Color.White)
                ) {
                    val (checkBoxRef, contentRef, deleteButtonRef, dividerRef) = createRefs()

                    Checkbox(
                        checked = isChecked.value,
                        onCheckedChange = {
                            isChecked.value = it

                            onUiAction.invoke(
                                CheckListAction.ClickCheckBox(
                                    selectedItem = uiState.itemsList[index].copy(isChecked = it)
                                )
                            )
                        },
                        enabled = true,
                        modifier = Modifier.constrainAs(checkBoxRef) {
                            start.linkTo(parent.start)
                            height = Dimension.fillToConstraints
                            centerVerticallyTo(parent)
                        }
                    )

                    Text(
                        text = items[index].content,
                        modifier = Modifier.constrainAs(contentRef) {
                            start.linkTo(checkBoxRef.end, 8.dp)
                            centerVerticallyTo(parent)
                        })

                    IconButton(
                        content = {
                            Icon(
                                imageVector = Icons.Sharp.Delete,
                                contentDescription = "Delete"
                            )
                        }, onClick = {
                            onUiAction.invoke(CheckListAction.DeleteItem(uiState.itemsList[index]))
                        }, modifier = Modifier.constrainAs(deleteButtonRef) {
                            end.linkTo(parent.end)
                            centerVerticallyTo(parent)
                        })

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(dividerRef) {
                                top.linkTo(checkBoxRef.bottom, 1.dp)
                                bottom.linkTo(parent.bottom)
                            },
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    onUiEvent: (event: CheckListAction) -> Unit,
) {
    CustomCircleIconButton(
        modifier = modifier.testTag("ADD_BUTTON_TAG"),
        imageVector = Icons.Rounded.Add,
        imageColor = Color.Black,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentDescription = "Add"
    ) {
        onUiEvent(CheckListAction.ClickAddItem)
    }
}

@Composable
private fun CustomProgress(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
}


@Preview
@Composable
private fun CheckListScreenPreview() {
    CheckListScreen(
        uiState = CheckListState(
            isLoading = false,
            itemsList = mutableListOf(
                CheckListItem(id = 0, content = "First"),
                CheckListItem(id = 1, content = "Second"),
                CheckListItem(id = 2, content = "Third"),
            )
        ), onUiAction = {}
    )
}