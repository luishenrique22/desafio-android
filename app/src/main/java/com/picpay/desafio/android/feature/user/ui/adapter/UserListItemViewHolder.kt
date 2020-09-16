package com.picpay.desafio.android.feature.user.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.feature.user.data.model.UserPayload
import com.picpay.desafio.android.feature.user.domain.model.UserModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(userModel: UserModel) {
        itemView.name.text = userModel.name
        itemView.username.text = userModel.userName
        itemView.progressBar.visibility = View.VISIBLE
        Picasso.get()
            .load(userModel.image)
            .error(R.drawable.ic_round_account_circle)
            .into(itemView.picture, object : Callback {
                override fun onSuccess() {
                    itemView.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    itemView.progressBar.visibility = View.GONE
                }
            })
    }
}