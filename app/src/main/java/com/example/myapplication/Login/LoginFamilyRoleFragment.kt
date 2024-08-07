package com.example.myapplication.Login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Memory.SelectGenreFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFamilyRoleBinding

class LoginFamilyRoleFragment : Fragment() {

    private var _binding: FragmentFamilyRoleBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RoleAdapter
    private var selectedRole: String = "역할을 선택해주세요"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFamilyRoleBinding.inflate(inflater, container, false)
        val view = binding.root

        val roles = mutableListOf("직접 작성할게요", "엄마", "아빠", "할머니", "할아버지")

        adapter = RoleAdapter(requireContext(), roles) { role ->
            selectedRole = role

            binding.tvSelectedRole.text = role
            binding.tvSelectedRole.setTextColor(Color.BLACK)
            binding.recyclerViewRoles.visibility = View.GONE

            // Enable the next button only if a valid role is selected
            binding.btnNext.isEnabled = selectedRole != "역할을 선택해주세요"
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

        // Initially, disable the next button
        binding.btnNext.isEnabled = false

        binding.btnNext.setOnClickListener {
            if (selectedRole != "역할을 선택해주세요") {
                // 프래그먼트 전환
                val fragment = LoginCompleteFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm2, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
