package com.example.myapplication.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFamilyInfoBinding
import com.example.myapplication.databinding.FragmentHomeSettingBinding
import kotlinx.coroutines.launch

class FamilyInfoFragment : Fragment() {
    private lateinit var binding: FragmentFamilyInfoBinding
    private lateinit var familyAdapter: FamilyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFamilyInfoBinding.inflate(inflater, container, false)
        setupRecyclerView()
        /*fetchFamilyInfo()*/
        return binding.root
    }

    private fun setupRecyclerView() {
        /*familyAdapter = FamilyAdapter()*/
        binding.rvFamilyInfo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = familyAdapter
        }
    }

    /*private fun fetchFamilyInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getFamilyInfo()
                if (response.isSuccess) {
                    familyAdapter.updateMembers(response.result.members)
                    binding.tvFamilySharingCode.text = response.result.babyCode
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }*/
}