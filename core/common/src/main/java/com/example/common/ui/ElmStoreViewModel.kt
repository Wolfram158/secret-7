package com.example.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import money.vivid.elmslie.core.store.Store

abstract class ElmStoreViewModel<Event : Any, Effect : Any, State : Any>(
    initialState: State,
    createStore: () -> Store<Event, Effect, State>
) : ViewModel() {
    val store by lazy(LazyThreadSafetyMode.NONE) {
        createStore().start()
    }

    val states by lazy(LazyThreadSafetyMode.NONE) {
        store.states
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                initialState
            )
    }

    val effects by lazy(LazyThreadSafetyMode.NONE) {
        store.effects
    }

    fun accept(event: Event) {
        store.accept(event)
    }

    override fun onCleared() {
        store.stop()
        super.onCleared()
    }
}