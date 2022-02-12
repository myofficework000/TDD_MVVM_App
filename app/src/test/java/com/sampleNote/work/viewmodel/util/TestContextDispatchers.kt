package com.sampleNote.work.viewmodel.util

import com.sampleNote.work.ContextDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

class TestContextDispatchers : ContextDispatchers() {
    @ExperimentalCoroutinesApi
    override val IO: CoroutineContext
        get() = Dispatchers.Unconfined

    @ExperimentalCoroutinesApi
    override val Main: CoroutineContext
        get() = Dispatchers.Unconfined
}