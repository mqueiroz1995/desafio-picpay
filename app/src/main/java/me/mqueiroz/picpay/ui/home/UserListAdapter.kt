package me.mqueiroz.picpay.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.utils.SingleLiveEvent

class UserListAdapter : RecyclerView.Adapter<UserListItemViewHolder>() {

    val onItemClickListener = SingleLiveEvent<User>()

    var users = emptyList<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_user, parent, false)

        return UserListItemViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}