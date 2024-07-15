package com.example.myapplication.Setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R


class ItemDetailDialogFragment : DialogFragment() {

    private var imageResourceId: Int = 0
    private var charname: String = ""
    private var description: String = ""
    private var changeListener: ChangeListener? = null

    companion object {
        private const val ARG_IMAGE_RESOURCE_ID = "image_resource_id"
        private const val ARG_CHAR_NAME = "char_name"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(imageResourceId: Int, charname: String, description: String): ItemDetailDialogFragment {
            val fragment = ItemDetailDialogFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_RESOURCE_ID, imageResourceId)
            args.putString(ARG_CHAR_NAME, charname)
            args.putString(ARG_DESCRIPTION, description)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomDialog)
        arguments?.let {
            imageResourceId = it.getInt(ARG_IMAGE_RESOURCE_ID)
            charname = it.getString(ARG_CHAR_NAME, "")
            description = it.getString(ARG_DESCRIPTION, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_detail_dialog, container, false)
        val imageView: ImageView = view.findViewById(R.id.detail_image_view)
        val charNameTextView: TextView = view.findViewById(R.id.tv_char_name)
        val descriptionTextView: TextView = view.findViewById(R.id.description_text_view)
        val closeButton: ImageView = view.findViewById(R.id.close_button)
        val changeButton: ImageView = view.findViewById(R.id.change_button)

        imageView.setImageResource(imageResourceId)
        charNameTextView.text = charname
        descriptionTextView.text = description

        closeButton.setOnClickListener {
            dismiss()
        }

        changeButton.setOnClickListener {
            changeListener?.onChangeSelected(imageResourceId)
            dismiss()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
    //변경하기버튼->추가
    interface ChangeListener {
        fun onChangeSelected(imageResourceId: Int)
    }
    fun setChangeListener(listener: ChangeListener) {
        changeListener = listener
    }
}