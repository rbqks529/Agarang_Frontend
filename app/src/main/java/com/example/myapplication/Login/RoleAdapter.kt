package com.example.myapplication.Login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class RoleAdapter(
    private val roles: MutableList<String>,
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_EDIT = 0
    private val VIEW_TYPE_ITEM = 1

    inner class EditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val editTextRole: EditText = itemView.findViewById(R.id.edit_text_role)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewRole: TextView = itemView.findViewById(R.id.text_view_role)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_role, parent, false)
        return when (viewType) {
            VIEW_TYPE_EDIT -> EditViewHolder(view)
            else -> ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EditViewHolder -> {
                holder.editTextRole.visibility = View.VISIBLE
                holder.editTextRole.setOnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val newRole = v.text.toString().trim()
                        if (newRole.isNotEmpty() && !roles.contains(newRole)) {
                            roles.add(1, newRole)
                            notifyDataSetChanged()
                            onItemSelected(newRole)
                        }
                        true
                    } else {
                        false
                    }
                }
            }
            is ItemViewHolder -> {
                val role = roles[position]
                holder.textViewRole.text = role
                holder.itemView.setOnClickListener {
                    onItemSelected(role)
                }
            }
        }
    }

    override fun getItemCount() = roles.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_EDIT else VIEW_TYPE_ITEM
    }
}