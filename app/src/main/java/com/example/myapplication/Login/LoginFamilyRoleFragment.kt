package com.example.myapplication.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentFamilyRoleBinding

class LoginFamilyRoleFragment : Fragment() {

    private var _binding: FragmentFamilyRoleBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RoleAdapter
    private var selectedRole: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFamilyRoleBinding.inflate(inflater, container, false)
        val view = binding.root

        val roles = mutableListOf("직접 작성할게요", "엄마", "아빠", "할머니", "할아버지", "기타")

        adapter = RoleAdapter(requireContext(), roles) { role ->
            selectedRole = role
            binding.tvSelectedRole.text = role
            binding.recyclerViewRoles.visibility = View.GONE
        }

        binding.recyclerViewRoles.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRoles.adapter = adapter

        binding.spinnerContainer.setOnClickListener {
            if (binding.recyclerViewRoles.visibility == View.VISIBLE) {
                binding.recyclerViewRoles.visibility = View.GONE
            } else {
                binding.recyclerViewRoles.visibility = View.VISIBLE
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
