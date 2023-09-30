package com.example.rickandmorty

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun <T> CoroutineScope.launchWith(
    t: T, block: suspend CoroutineScope.(T) -> Unit
) = launch { block(t) }