package com.picpay.desafio.android.feature.user.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.feature.user.data.model.UserPayload
import com.picpay.desafio.android.feature.user.domain.model.UserModel

class UserListDiffCallback(
    private val oldList: List<UserModel>,
    private val newList: List<UserModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].userName == newList[newItemPosition].userName
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}