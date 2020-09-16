package com.picpay.desafio.android.feature.user.ui.adapter

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.feature.user.domain.model.UserModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView), LifecycleObserver {


    fun bind(
        userModel: UserModel,
        lifecycleOwner: Lifecycle
    ) {
        lifecycleOwner.addObserver(this)
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

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun cancelRequest(){
        Picasso.get().cancelRequest(itemView.picture)
    }
}