package me.mqueiroz.picpay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.common.entities.Receipt
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.di.injector
import me.mqueiroz.picpay.utils.StringResource
import me.mqueiroz.picpay.utils.addTextWatcher

class HomeFragment : Fragment() {

    private val args: HomeFragmentArgs by navArgs()

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

        args.receipt?.let { receipt ->
            showReceiptDialog(receipt, args.card!!)
        }
    }

    private fun initializeRecyclerView() {
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        adapter.onItemClickListener.observe(this, Observer { viewModel.onUserSelected(it) })

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

        viewModel.navigate.observe(this, Observer { action ->
            when (action) {
                is HomeFragmentNavigation.CardPrimingScreen -> navigateToCardPriming(action.user)
                is HomeFragmentNavigation.PaymentScreen -> navigateToPayment(action.user, action.card)
            }
        })

        home_search.addTextWatcher {
            viewModel.onQueryChanged(it)
        }
    }

    private fun onLoading() {
        user_list_progress_bar.visibility = View.VISIBLE
    }

    private fun onError(message: StringResource) {
        user_list_progress_bar.visibility = View.GONE
        Snackbar.make(fragment_home_root, getString(message.id), Snackbar.LENGTH_SHORT).show()
    }

    private fun onLoaded(users: List<User>) {
        user_list_progress_bar.visibility = View.GONE
        adapter.users = users
    }

    private fun navigateToCardPriming(user: User) {
        val action = HomeFragmentDirections
                .actionHomeFragmentToCardRegisterPrimingFragment(user)
        findNavController().navigate(action)
    }

    private fun navigateToPayment(user: User, card: Card) {
        val action = HomeFragmentDirections
                .actionHomeFragmentToPaymentFragment(user, card)
        findNavController().navigate(action)
    }

    private fun showReceiptDialog(receipt: Receipt, card: Card) {
        val action = HomeFragmentDirections
                .actionHomeFragmentToReceiptDialog(receipt, card)
        findNavController().navigate(action)
    }
}