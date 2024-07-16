package com.example.myapplication.Memory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.InstrumentItemBinding
import com.google.android.material.shape.ShapeAppearanceModel

class InstrumentAdapter(
    private val instruments: List<InstrumentData>,
    private val onItemClick: (InstrumentData) -> Unit
) : RecyclerView.Adapter<InstrumentAdapter.InstrumentViewHolder>() {

    private var selectedPosition: Int = -1

    inner class InstrumentViewHolder(val binding: InstrumentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(instrument: InstrumentData, position: Int) {
            binding.tvInstrument.text = instrument.instrumentName
            binding.ivInstrument.setImageResource(instrument.imageResId)

            if (selectedPosition == position) {
                binding.ivInstrumentBackground.setImageResource(R.drawable.ic_instrument_background_selected)
                binding.ivInstrument.setImageResource(R.drawable.ic_check)
            } else {
                binding.ivInstrumentBackground.setImageResource(R.drawable.ic_instrument_background)
                binding.ivInstrument.visibility = View.VISIBLE
            }

            if (instrument.shouldCrop) {
                binding.ivInstrument.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCornerSizes(ShapeAppearanceModel.PILL)
                    .build()
            } else {
                binding.ivInstrument.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .build()
            }

            itemView.setOnClickListener {
                if (selectedPosition == position) {
                    selectedPosition = -1
                } else {
                    selectedPosition = position
                }
                notifyDataSetChanged()
                onItemClick(instrument)
            }
        }
    }

    override fun onBindViewHolder(holder: InstrumentViewHolder, position: Int) {
        holder.bind(instruments[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstrumentViewHolder {
        val binding = InstrumentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstrumentViewHolder(binding)
    }


    override fun getItemCount() = instruments.size
}