package com.henriquevieira.core.router

sealed class Routes {
    object Main: Routes()
    object Home: Routes()
}
