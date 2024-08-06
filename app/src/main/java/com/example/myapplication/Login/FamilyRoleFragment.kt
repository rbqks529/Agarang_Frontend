package com.example.myapplication.Login

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class FamilyRoleFragment : Fragment() {

    private lateinit var spinnerRole: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_family_role, container, false)

        // Initialize the spinner
        spinnerRole = view.findViewById(R.id.spinner_role)

        // Define the list of roles
        val roles = listOf("직접 입력", "엄마", "아빠", "할머니", "할아버지")
        // Create an adapter with the list of roles
        val adapter = CustomSpinnerAdapter(requireContext(), roles.toMutableList())
        spinnerRole.adapter = adapter

        // Set a listener for item selection
        spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                adapter.setSelectedPosition(position)
                val selectedRole = adapter.getSelectedRole()
                // Use or save the selected role here
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        return view
    }
}

class CustomSpinnerAdapter(context: Context, private val roles: MutableList<String>) :
    ArrayAdapter<String>(context, R.layout.spinner_item, roles) {

    private var editTextView: EditText? = null
    private var selectedPosition: Int = 0

    // Method to get the dropdown view
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return when {
            position == 0 -> getEditTextView(parent)
            else -> getTextView(position, convertView, parent)
        }
    }

    // Method to get the edit text view for custom input
    private fun getEditTextView(parent: ViewGroup): EditText {
        return (editTextView ?: EditText(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            hint = "역할을 직접 입력하세요"
            setPadding(30, 20, 30, 20)
        }).also { editTextView = it }
    }

    // Method to get the text view for predefined roles
    private fun getTextView(position: Int, convertView: View?, parent: ViewGroup): TextView {
        return (convertView as? TextView ?: TextView(context)).apply {
            text = getItem(position)
            setTextColor(Color.BLACK)
            textSize = 16f
            typeface = Typeface.create("Pretendard", Typeface.NORMAL)
            setPadding(30, 20, 30, 20)
            if (position == selectedPosition) {
                background = ContextCompat.getDrawable(context, R.drawable.ic_login_selected)
            } else {
                background = null
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }
    }

    // Method to get the main view for the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return (super.getView(position, convertView, parent) as TextView).apply {
            if (position == 0 && editTextView?.text?.isNotEmpty() == true) {
                text = editTextView?.text
            }
            if (text.isNullOrEmpty()) {
                text = "역할을 선택해주세요"
                setTextColor(Color.GRAY)
            } else {
                setTextColor(Color.BLACK)
            }
            textSize = 16f
            typeface = Typeface.create("Pretendard", Typeface.NORMAL)
        }
    }

    // Method to set the selected position
    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    // Method to get the selected role based on the position
    fun getSelectedRole(): String {
        return if (selectedPosition == 0) {
            editTextView?.text?.toString() ?: ""
        } else {
            getItem(selectedPosition) ?: ""
        }
    }
}
