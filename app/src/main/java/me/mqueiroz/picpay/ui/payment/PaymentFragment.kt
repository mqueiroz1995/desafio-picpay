package me.mqueiroz.picpay.ui.payment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.toolbar
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.di.injector
import me.mqueiroz.picpay.ui.MainActivity
import me.mqueiroz.picpay.ui.SharedViewModel
import java.lang.Exception

class PaymentFragment : Fragment() {

    private val viewModel: PaymentViewModel by lazy {
        ViewModelProviders
                .of(this, injector.paymentViewModelFactory())
                .get(PaymentViewModel::class.java)
    }

    private lateinit var sharedViewModel: SharedViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        sharedViewModel = ViewModelProviders
                .of((context as MainActivity), injector.sharedViewModelFactory())
                .get(SharedViewModel::class.java)

        val payee = sharedViewModel.payee.value!!
        val card = sharedViewModel.card.value!!
        viewModel.setState(payee, card)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        bindPayee()
        bindCard()
    }

    private fun bindPayee() {
        viewModel.payee.observe(this, Observer {
            payment_username.text = it.username

            payment_picture_progress_bar.visibility = View.VISIBLE
            Picasso.get()
                    .load(it.img)
                    .error(R.drawable.ic_round_account_circle)
                    .into(payment_picture, object : Callback {
                        override fun onSuccess() {
                            payment_picture_progress_bar.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            payment_picture_progress_bar.visibility = View.GONE
                        }
                    })
        })
    }

    private fun bindCard() {
        viewModel.card.observe(this, Observer {
            val cardEnding = it.cardNumber.substring(it.cardNumber.length - 4)
            payment_card.text = "X X X $cardEnding"
        })
    }
}