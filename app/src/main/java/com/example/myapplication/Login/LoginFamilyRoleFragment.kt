package com.example.myapplication.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class LoginFamilyRoleFragment : Fragment() {

    private lateinit var spinnerRole: Spinner
    private lateinit var adapter: CustomSpinnerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_family_role, container, false)

        spinnerRole = view.findViewById(R.id.spinner_role)

        val roles = mutableListOf("역할을 선택해주세요", "엄마", "아빠", "할머니", "할아버지", "기타")
        adapter = CustomSpinnerAdapter(requireContext(), roles)
        spinnerRole.adapter = adapter

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