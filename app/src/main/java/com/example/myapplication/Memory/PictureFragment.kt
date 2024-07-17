package com.example.myapplication.Memory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPictureBinding


class PictureFragment : Fragment() {
    private lateinit var binding:FragmentPictureBinding
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPictureBinding.inflate(inflater,container,false)

        // Initialize the launcher
        galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    //binding.ivGallery.setImageURI(selectedImageUri)
                    //번들로 넘기거나 데이터를 저장하거나

                    //다음 fragment로 전환
                    val fragment=PicAssociationFragment()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.memory_frm,fragment)
                        .commit()
                }
            }
        }

        binding.ivGallery.setOnClickListener {
            openGallery()
        }

        return binding.root
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        galleryLauncher.launch(intent)
    }

}