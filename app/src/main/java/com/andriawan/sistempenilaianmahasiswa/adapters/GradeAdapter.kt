package com.andriawan.sistempenilaianmahasiswa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andriawan.sistempenilaianmahasiswa.databinding.ItemGradesBinding
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.utils.RecyclerDiffUtil

/**
 * Showing detail grade of user by their subject and type
 *
 * @param onGradeClicked - For showing edit dialog
 *
 * @author Naufal Fawwaz Andriawan
 */
class GradeAdapter(
    private val onGradeClicked: (student: Grades) -> Unit
) : RecyclerView.Adapter<GradeAdapter.ViewHolder>() {

    private var gradeList = emptyList<Grades>()

    class ViewHolder(val binding: ItemGradesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(grade: Grades) {
            binding.nameTextView.text = grade.subject
            binding.scoreNumberTextView.text = grade.grade.toString()
        }

        companion object {
            fun fromParent(parent: ViewGroup): ViewHolder {
                val binding = ItemGradesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grade = gradeList[position]
        holder.binding.layoutLinear.setOnClickListener {
            onGradeClicked.invoke(grade)
        }
        holder.bind(grade)
    }

    override fun getItemCount(): Int = gradeList.size

    fun setData(newList: List<Grades>) {
        val diffUtil = RecyclerDiffUtil(gradeList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        gradeList = newList
        result.dispatchUpdatesTo(this)
    }
}