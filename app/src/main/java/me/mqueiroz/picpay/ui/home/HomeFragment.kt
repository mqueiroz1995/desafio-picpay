package me.mqueiroz.picpay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.di.injector
import me.mqueiroz.picpay.utils.StringResource

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders
                .of(this, injector.homeViewModelFactory())
                .get(HomeViewModel::class.java)
    }

    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) viewModel.loadUsers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()

        bindViewModel()
    }

    private fun initializeRecyclerView() {
        adapter = UserListAdapter()
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
    }

    private fun bindViewModel() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is HomeFragmentState.Loading -> onLoading()
                is HomeFragmentState.Error -> onError(state.message)
                is HomeFragmentState.Loaded -> onLoaded(state.users)
            }
        })
    }

    private fun onLoading() {

    }

    private fun onError(message: StringResource) {

    }

    private fun onLoaded(users: List<User>) {
        adapter.users = users
    }
}