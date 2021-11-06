package com.example.note_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*


class recycler (val activity: MainActivity): RecyclerView.Adapter<recycler.ItemHolder>() {

    private var UserInput = emptyList<noteInfo>()

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val userinput=UserInput[position]
        holder.itemView.apply {
            tvUserNote.text =userinput.note
            imageButton4.setOnClickListener {
                activity.openDialog(userinput.id)

            }
            imageButton5.setOnClickListener {
                activity.deleteNotes(userinput.id)
            }

        }
    }

    override fun getItemCount()= UserInput.size

    fun update(notes: List<noteInfo>){
        println("UPDATING DATA")
        this.UserInput = notes
        notifyDataSetChanged()
    }

}