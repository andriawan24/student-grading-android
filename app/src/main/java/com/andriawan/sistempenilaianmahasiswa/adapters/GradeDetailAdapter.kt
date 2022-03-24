package com.andriawan.sistempenilaianmahasiswa.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andriawan.sistempenilaianmahasiswa.databinding.ItemGradeBinding
import com.andriawan.sistempenilaianmahasiswa.utils.Helper
import com.andriawan.sistempenilaianmahasiswa.utils.RecyclerDiffUtil
import com.andriawan.sistempenilaianmahasiswa.utils.hide
import com.andriawan.sistempenilaianmahasiswa.utils.visible


/**
 * For showing all of student's grades by subject
 *
 * @param onDeleteClickGradeClick - For showing delete dialog
 *
 * @author Naufal Fawwaz Andriawan
 */
class GradeDetailAdapter(
    private val onDeleteClickGradeClick: (subject: String) -> Unit,
    private val onEditClickGradeClick: (subject: String) -> Unit
) : RecyclerView.Adapter<GradeDetailAdapter.ViewHolder>() {

    private var gradeList = emptyList<Pair<String, Pair<String, Double>>>()

    class ViewHolder(val binding: ItemGradeBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(grade: Pair<String, Pair<String, Double>>) {
            binding.nameTextView.text = grade.first
            binding.scoreTextTextView.text = "${String.format("%.2f", grade.second.second)} (${grade.second.first})"
            binding.keteranganTextTextView.text = Helper.getKeterangan(
                binding.scoreTextTextView,
                binding.keteranganTextTextView.context,
                grade.second.first
            )
        }

        companion object {
            fun fromParent(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemGradeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = gradeList[position]
        holder.binding.layoutLinear.setOnClickListener {
            if (holder.binding.detailLayout.isVisible) {
                holder.binding.detailLayout.hide()
                holder.binding.dropdown.rotation = 90.0F
            } else {
                holder.binding.detailLayout.visible()
                holder.binding.dropdown.rotation = 270.0F
            }
        }

        holder.binding.deleteGradeButton.setOnClickListener {
            onDeleteClickGradeClick.invoke(student.first)
        }

        holder.binding.editGradeButton.setOnClickListener {
            onEditClickGradeClick.invoke(student.first)
        }

        holder.bind(student)
    }

    override fun getItemCount(): Int = gradeList.size

    fun setData(newList: List<Pair<String, Pair<String, Double>>>) {
        val diffUtil = RecyclerDiffUtil(gradeList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        gradeList = newList
        result.dispatchUpdatesTo(this)
    }
}