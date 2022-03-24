package com.andriawan.sistempenilaianmahasiswa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andriawan.sistempenilaianmahasiswa.databinding.ItemStudentBinding
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.utils.RecyclerDiffUtil

/**
 * Showing student list
 *
 * @param onStudentClicked - Go to detail page
 * @param onStudentLongClicked - Shows the edit or delete dialog
 *
 * @author Naufal Fawwaz Andriawan
 */
class StudentAdapter(
    private val onStudentClicked: (student: Student) -> Unit,
    private val onStudentLongClicked: (student: Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var studentList = emptyList<Student>()

    class ViewHolder(val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.nameTextView.text = student.name
            binding.nimTextView.text = student.nim
        }

        companion object {
            fun fromParent(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = studentList[position]
        holder.binding.layoutLinear.setOnClickListener {
            onStudentClicked.invoke(student)
        }
        holder.binding.layoutLinear.setOnLongClickListener {
            onStudentLongClicked.invoke(student)
            true
        }
        holder.bind(student)
    }

    override fun getItemCount(): Int = studentList.size

    fun setData(newList: List<Student>) {
        val diffUtil = RecyclerDiffUtil(studentList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        studentList = newList
        result.dispatchUpdatesTo(this)
    }
}