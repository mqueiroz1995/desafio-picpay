package me.mqueiroz.picpay.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.utils.SingleLiveEvent
import java.lang.Exception

class UserListItemViewHolder(
        itemView: View,
        private val onClickListener: SingleLiveEvent<User>
) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        itemView.name.text = user.name
        itemView.username.text = user.username
        itemView.progressBar.visibility = View.VISIBLE
        Picasso.get()
                .load(user.img)
                .error(R.drawable.ic_round_account_circle)
                .into(itemView.picture, object : Callback {
                    override fun onSuccess() {
                        itemView.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        itemView.progressBar.visibility = View.GONE
                    }
                })

        itemView.setOnClickListener {
            onClickListener.value = user
        }
    }
}