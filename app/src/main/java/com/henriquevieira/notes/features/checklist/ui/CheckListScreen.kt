package com.henriquevieira.notes.features.checklist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.commonsui.header.CustomHeader
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListState

@Composable
fun CheckListScreen(
    uiState: CheckListState,
    onUiAction: (action: CheckListAction) -> Unit,
) {
    AppTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (headerRef, listRef) = createRefs()

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
                    top.linkTo(headerRef.bottom, 16.dp)
                }
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
        uiState.itemsList?.let { item ->
            items(item.size) { index ->
                val isChecked = remember { mutableStateOf(uiState.itemsList[index].isChecked) }
                ConstraintLayout(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(5.dp)
                        .background(color = if (isChecked.value == true) Color.LightGray else Color.White)
                ) {
                    val (checkBoxRef, contentRef, dividerRef) = createRefs()

                    Checkbox(
                        checked = isChecked.value ?: false,
                        onCheckedChange = {
                            isChecked.value = it

                            onUiAction.invoke(
                                CheckListAction.ClickCheckBox(
                                    selectedItem = uiState.itemsList[index]
                                )
                            )
                        },
                        enabled = true,
                        modifier = Modifier.constrainAs(checkBoxRef) {
                            start.linkTo(parent.start, 4.dp)
                            height = Dimension.fillToConstraints
                            centerVerticallyTo(parent)
                        }
                    )

                    Text(
                        text = item[index].content ?: "",
                        modifier = Modifier.constrainAs(contentRef) {
                            start.linkTo(checkBoxRef.end, 8.dp)
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

@Preview
@Composable
private fun CheckListScreenPreview() {
    CheckListScreen(
        uiState = CheckListState(
            isLoading = false,
            itemsList = mutableListOf(
                CheckListState.Item(id = 0, content = "First"),
                CheckListState.Item(id = 1, content = "Second"),
                CheckListState.Item(id = 2, content = "Third")
            )
        ), onUiAction = {}
    )
}