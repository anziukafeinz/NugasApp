package com.example.theapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter untuk RecyclerView untuk menampilkan daftar tugas
class TaskAdapter(private val tasks: MutableList<Task>, private val listener: OnTaskItemClickListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    // Interface untuk menangani klik pada tombol edit dan hapus
    interface OnTaskItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    // ViewHolder untuk setiap item tugas pada RecyclerView
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val additionalDescriptionTextView: TextView = itemView.findViewById(R.id.additionalDescriptionTextView)
        val editButton: TextView = itemView.findViewById(R.id.editButton)
        val deleteButton: TextView = itemView.findViewById(R.id.deleteButton)
    }

    // Membuat ViewHolder baru berdasarkan layout task_item.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    // Bind data ke ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]

        // Menampilkan informasi tugas ke dalam TextView di ViewHolder
        holder.titleTextView.text = currentTask.title
        holder.descriptionTextView.text = currentTask.description
        holder.additionalDescriptionTextView.text = currentTask.additionalDescription

        // Menangani klik pada tombol edit dan hapus
        holder.editButton.setOnClickListener {
            listener.onEditClick(position)
        }
        holder.deleteButton.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    //  Menjumlahkan total item dalam daftar tugas
    override fun getItemCount() = tasks.size
}
