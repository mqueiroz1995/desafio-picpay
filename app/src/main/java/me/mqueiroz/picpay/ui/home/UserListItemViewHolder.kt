package me.mqueiroz.picpay.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_user.view.*
import me.mqueiroz.picpay.common.entities.User

class UserListItemViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        itemView.textView.text = user.name
    }
}