package com.example.myapplication.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFamilyRoleBinding

class LoginFamilyRoleFragment : Fragment() {

    lateinit var binding: FragmentFamilyRoleBinding
    private lateinit var adapter: RoleAdapter
    private var selectedRole: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFamilyRoleBinding.inflate(inflater, container, false)

        binding.recyclerViewRoles.layoutManager = LinearLayoutManager(context)

        val roles = mutableListOf("직접 작성할게요", "엄마", "아빠", "할머니", "할아버지", "기타")

        adapter = RoleAdapter(roles) { role ->
            selectedRole = role
            binding.tvSelectedRole.text = role
            binding.recyclerViewRoles.visibility = View.GONE
        }

        binding.recyclerViewRoles.adapter = adapter

        binding.spinnerContainer.setOnClickListener {
            if (binding.recyclerViewRoles.visibility == View.VISIBLE) {
                binding.recyclerViewRoles.visibility = View.GONE
            } else {
                binding.recyclerViewRoles.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}