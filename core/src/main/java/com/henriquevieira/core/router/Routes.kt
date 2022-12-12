package com.henriquevieira.core.router

sealed class Routes {
    object Note: Routes()
    object Home: Routes()
}
