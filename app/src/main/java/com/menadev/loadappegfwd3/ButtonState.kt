package com.menadev.loadappegfwd3

sealed class ButtonState{
    object Clicked:ButtonState()
    object Loading:ButtonState()
    object Completed:ButtonState()

}
