package com.shs.ui.utils.commons.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBoundViewHolder<T : ViewDataBinding>(val binding: T) :
        RecyclerView.ViewHolder(binding.root)
