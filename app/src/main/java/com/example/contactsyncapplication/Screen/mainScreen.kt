package com.example.contactsyncapplication.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class mainScreen {
}

@Composable
fun MainActivityScreen(addContact : ()->Unit){
    Column(verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = Modifier.fillMaxSize()){

        Button(onClick = { addContact() }) {
            Text(text = "Sync Contact" , fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = { }) {
            Text(text = "Delete Contact" , fontSize = 14.sp)
        }

    }
}