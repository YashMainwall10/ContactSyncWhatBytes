package com.example.contactsyncapplication

import android.Manifest
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contactsyncapplication.Screen.MainActivityScreen
import com.example.contactsyncapplication.Screen.mainScreen
import com.example.contactsyncapplication.Utils.contacts
import com.example.contactsyncapplication.Utils.toAddNewContact.addContact
import com.example.contactsyncapplication.ui.theme.ContactSyncApplicationTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class MainActivity2 : ComponentActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var mainActivityViewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityViewModel = MainActivityViewModel()
        val contentResolver = contentResolver
        super.onCreate(savedInstanceState)

        db = FirebaseFirestore.getInstance()

        when {
            ContextCompat.checkSelfPermission(
                this@MainActivity2,
                Manifest.permission.WRITE_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {

            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.WRITE_CONTACTS) -> {
            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_CONTACTS),
                    100)
            }
        }

        fun addContact(){
            var i = 0
            db.collection("Contacts")
                .get()
                .addOnSuccessListener {
                    for(document in it){
                        val cont = document.toObject<contacts>()
                        addContact(cont.name , cont.phoneNumber , cont.eMail  ,this@MainActivity2 , contentResolver)
                        mainActivityViewModel.index.value++
                    }

                    Toast.makeText(this@MainActivity2 , "Contacts Updated" , Toast.LENGTH_LONG).show()
                    mainActivityViewModel.index.value = 100
                }
                .addOnFailureListener{
                    Toast.makeText(this@MainActivity2 , "Failed" , Toast.LENGTH_SHORT).show()
                }

        }

        setContent {
            Column(modifier = Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.SpaceEvenly){
                progressBar(mainActivityViewModel = mainActivityViewModel)
                app(::addContact)

            }

        }
    }

}

@Composable
fun app(addContact : ()->Unit){
    MainActivityScreen(addContact)
}

@Composable
fun progressBar(mainActivityViewModel: MainActivityViewModel){
    remember {
        mainActivityViewModel.index.value
    }
    LinearProgressIndicator(progress = (mainActivityViewModel.index.value.toFloat()/100) , modifier = Modifier.fillMaxWidth())
}

