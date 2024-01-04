package com.example.theapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), TaskAdapter.OnTaskItemClickListener {

    // Daftar tugas untuk menyimpan daftar tugas
    private val taskList = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Inisialisasi RecyclerView dan TaskAdapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        taskAdapter = TaskAdapter(taskList, this)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Menangani penambahan tugas baru
        val fabAdd: FloatingActionButton = findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener {
            showAddTaskDialog()
        }
        // Menambahkan beberapa tugas contoh ke dalam daftar
        taskList.add(Task(1, "Tip", "Tekan tombol + dibawah untuk menambahkan tugas", "Tekan tombol pensil untuk mengedit, dan tekan tombol hapus untuk menghapus tugas"))

        // Memberi tahu adapter tentang perubahan dalam kumpulan data
        taskAdapter.notifyDataSetChanged()
    }

    // Listener klik untuk tombol edit pada item RecyclerView
    override fun onEditClick(position: Int) {
        showEditTaskDialog(position)
    }

    // Listener klik untuk tombol hapus pada item RecyclerView
    override fun onDeleteClick(position: Int) {
        // Menghapus tugas
        taskList.removeAt(position)
        taskAdapter.notifyDataSetChanged()
    }

    // Fungsi untuk menampilkan dialog untuk menambahkan tugas baru
    private fun showAddTaskDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_add_task, null)
        dialogBuilder.setView(dialogView)

        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val descriptionEditText = dialogView.findViewById<EditText>(R.id.editTextDescription)
        val additionalDescriptionEditText = dialogView.findViewById<EditText>(R.id.editTextAdditionalDescription)

        dialogBuilder.setTitle("Tambah Tugas")
        dialogBuilder.setPositiveButton("Tambah") { _, _ ->
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val additionalDescription = additionalDescriptionEditText.text.toString()

            val newId = taskList.size + 1

            // Menambahkan tugas baru kedalam list
            taskList.add(Task(newId, title, description, additionalDescription))
            taskAdapter.notifyDataSetChanged()
        }
        dialogBuilder.setNegativeButton("Batal") { _, _ -> }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
    // Fungsi untuk menampilkan dialog untuk mengedit tugas yang ada
    private fun showEditTaskDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_add_task, null)
        dialogBuilder.setView(dialogView)

        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val descriptionEditText = dialogView.findViewById<EditText>(R.id.editTextDescription)
        val additionalDescriptionEditText = dialogView.findViewById<EditText>(R.id.editTextAdditionalDescription)

        val currentTask = taskList[position]
        titleEditText.setText(currentTask.title)
        descriptionEditText.setText(currentTask.description)
        additionalDescriptionEditText.setText(currentTask.additionalDescription)

        dialogBuilder.setTitle("Edit Tugas")
        dialogBuilder.setPositiveButton("Update") { _, _ ->
            val updatedTitle = titleEditText.text.toString()
            val updatedDescription = descriptionEditText.text.toString()
            val updatedAdditionalDescription = additionalDescriptionEditText.text.toString()

            val updatedTask = Task(currentTask.id, updatedTitle, updatedDescription, updatedAdditionalDescription)
            taskList[position] = updatedTask
            taskAdapter.notifyDataSetChanged()
        }
        dialogBuilder.setNegativeButton("Batal") { _, _ -> }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}

