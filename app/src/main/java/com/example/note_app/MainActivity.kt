package com.example.note_app
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    //  val userNote = ArrayList<String>()
    lateinit var ev1: EditText

    lateinit var button: Button
    lateinit var rvAdapter: recycler
    lateinit var notes: ArrayList<noteInfo>
    lateinit var myRv: RecyclerView
    lateinit var model: viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notes = arrayListOf()

        model = ViewModelProvider(this).get(viewModel::class.java)
        model.getNotes().observe(this, {
                notes -> rvAdapter.update(notes)
        })

        ev1 = findViewById(R.id.tv1)
        val layout = findViewById<ConstraintLayout>(R.id.layout)


        myRv = findViewById(R.id.rvMain)
        layout.setBackgroundResource(R.drawable.background)
        button = findViewById(R.id.button)


        rvAdapter = recycler(this)
        myRv.adapter = rvAdapter
        myRv.layoutManager = LinearLayoutManager(this)

        button.setOnClickListener {
            if (ev1.text.isNotBlank()) {
                // var n=NotesData(0,ev1.text.toString())
                model.addNote(noteInfo(ev1.text.toString(),""))
                ev1.text.clear()

            } else {
                Toast.makeText(applicationContext, "write a note", Toast.LENGTH_LONG).show()

            }

        }
        model.getAllNotes()

    }


    fun openDialog(id: String) {

        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        dialogBuilder.setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                model.editNote(id, updatedNote.text.toString())


            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()

    }

    fun deleteNotes(id: String) {
        model.deleteNote(id)


    }


}