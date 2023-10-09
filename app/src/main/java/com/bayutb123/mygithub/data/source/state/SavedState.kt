package com.bayutb123.mygithub.data.source.state

sealed class SavedState {
    object NotSaved : SavedState()
    data class Saved(val state : Boolean) : SavedState()
}