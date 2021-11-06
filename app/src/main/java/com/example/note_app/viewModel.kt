package com.example.note_app

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class viewModel(application: Application): AndroidViewModel(application) {
    private val db = Firebase.firestore
    val TAG="data"
    private var notes: MutableLiveData<List<noteInfo>> = MutableLiveData()


    fun getNotes(): LiveData<List<noteInfo>> {

        return notes
    }
    fun getAllNotes() {
        val list = arrayListOf<noteInfo>()
        db.collection("Notes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result)
                    document.data.map { (key, value) ->

                        list.add(noteInfo(value.toString(),document.id))
                    }
                notes.postValue(list)
            }
            .addOnFailureListener {
                notes.postValue(list)
            }
    }

    fun addNote(note: noteInfo){

        var noteText=note.note
        db.collection("Notes")
            .add(hashMapOf(
                "Note" to noteText
            ))
            .addOnSuccessListener { document ->
                //Toast.makeText(applicationContext, "data is served", Toast.LENGTH_LONG).show()
                Log.d(TAG, "DocumentSnapshot added with ID: ${document.id}")
                getAllNotes()

            }


            .addOnFailureListener { e ->
                //  Toast.makeText(applicationContext, "fail to serve", Toast.LENGTH_LONG).show()
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun editNote(id: String, note: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("Notes").document(id).update("Note",note)
            getAllNotes()
        }
    }

    fun deleteNote(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("Notes").document(id).delete()
        }
        getAllNotes()

    }
}