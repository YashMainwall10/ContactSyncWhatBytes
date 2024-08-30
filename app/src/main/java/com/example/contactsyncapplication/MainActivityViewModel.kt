package com.example.contactsyncapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    val index = mutableStateOf(1)
}