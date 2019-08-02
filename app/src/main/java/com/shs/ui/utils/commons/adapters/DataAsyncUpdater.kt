package com.shs.ui.utils.commons.adapters

import android.os.AsyncTask
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil

class DataAsyncUpdater<T, V : ViewDataBinding>(private val mDataBoundRecyclerAdapter:
                                               DataBoundRecyclerAdapter<T, V>
)
    : AsyncTask<T, Unit, DiffUtil.DiffResult>() {

    private var mStartVersion: Int = 0

    private lateinit var mOldItems: List<T>

    private lateinit var mUpdate: List<T>

    override fun doInBackground(vararg update: T)
            : DiffUtil.DiffResult {
        mStartVersion = mDataBoundRecyclerAdapter.dataVersion
        mOldItems = mDataBoundRecyclerAdapter.items
        mUpdate = update.toList()
        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize(): Int = mDataBoundRecyclerAdapter.items.size

            override fun getNewListSize(): Int = mUpdate.size

            override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
            ): Boolean = mDataBoundRecyclerAdapter
                    .areContentsTheSame(mOldItems[oldItemPosition], mUpdate[newItemPosition])

            override fun areItemsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
            ): Boolean = mDataBoundRecyclerAdapter
                    .areItemsTheSame(mOldItems[oldItemPosition], mUpdate[newItemPosition])
        })
    }

    override fun onPostExecute(result: DiffUtil.DiffResult?) {
        if (mStartVersion != mDataBoundRecyclerAdapter.dataVersion) return
        mDataBoundRecyclerAdapter.items = mUpdate
        result?.dispatchUpdatesTo(mDataBoundRecyclerAdapter)
    }
}
