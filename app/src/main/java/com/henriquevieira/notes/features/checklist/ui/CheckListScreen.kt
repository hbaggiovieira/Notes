package com.henriquevieira.notes.features.checklist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListState

@Composable
fun CheckListScreen(
    modifier: Modifier = Modifier,
    uiState: CheckListState,
    onUiAction: (action: CheckListAction) -> Unit,
) {
    AppTheme {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
        ) {
            val (headerRef, listRef, addButtonRef, saveButtonRef) = createRefs()

            CustomHeader(
                title = "Checklist",
                modifier = Modifier.constrainAs(headerRef) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                },
                isLoading = uiState.isLoading,
                onCloseButtonClick = { onUiAction.invoke(CheckListAction.Close) }
            )

            ListField(
                uiState = uiState,
                onUiAction = onUiAction,
                modifier = Modifier.constrainAs(listRef) {
                    top.linkTo(headerRef.bottom)
                    bottom.linkTo(addButtonRef.top)
                    height = Dimension.fillToConstraints
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("SAVE_BUTTON_TAG")
                    .constrainAs(saveButtonRef) {
                        start.linkTo(parent.start)
                        end.linkTo(addButtonRef.start, 4.dp)
                        bottom.linkTo(parent.bottom)
                    },
                content = {
                    Text(stringResource(R.string.save))
                },
                onClick = { onUiAction(CheckListAction.Save) }
            )

            AddButton(
                onUiEvent = onUiAction,
                modifier = Modifier.constrainAs(addButtonRef) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }.padding(4.dp),
                uiState = uiState
            )
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
        items(uiState.itemsList.size) { index ->
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
                            CheckListAction.ToggleCheck(
                                index = index,
                                isChecked = it
                            )
                        )
                    },
                    enabled = !uiState.isLoading,
                    modifier = Modifier.constrainAs(checkBoxRef) {
                        start.linkTo(parent.start)
                        height = Dimension.fillToConstraints
                        centerVerticallyTo(parent)
                    }
                )

                Text(
                    text = uiState.itemsList[index].content,
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
                        onUiAction.invoke(CheckListAction.DeleteItem(index = index))
                    }, modifier = Modifier.constrainAs(deleteButtonRef) {
                        end.linkTo(parent.end)
                        centerVerticallyTo(parent)
                    }, enabled = !uiState.isLoading
                )

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

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    uiState: CheckListState,
    onUiEvent: (event: CheckListAction) -> Unit,
) {
    CustomCircleIconButton(
        modifier = modifier.testTag("ADD_BUTTON_TAG"),
        imageVector = Icons.Rounded.Add,
        imageColor = Color.Black,
        isEnabled = !uiState.isLoading,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentDescription = "Add"
    ) {
        onUiEvent(CheckListAction.OpenAddItem)
    }
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