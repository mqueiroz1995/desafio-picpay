package me.mqueiroz.picpay.ui.payment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.toolbar
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.di.injector
import me.mqueiroz.picpay.ui.MainActivity
import me.mqueiroz.picpay.ui.SharedViewModel
import java.lang.Exception
import me.mqueiroz.picpay.utils.CurrencyTextWatcher
import me.mqueiroz.picpay.utils.StringResource


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

        payment_value.addTextChangedListener(CurrencyTextWatcher(payment_value))

        bindViewModel()
    }

    private fun bindViewModel() {
        bindPayee()
        bindCard()

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is PaymentFragmentState.PaymentDisabled -> setEnabled(false)
                is PaymentFragmentState.PaymentEnabled -> setEnabled(true)
                is PaymentFragmentState.ProcessingPayment -> setProcessing(true)
                is PaymentFragmentState.PaymentSuccess -> onSuccess()
                is PaymentFragmentState.PaymentError -> onError(state.message)
            }
        })

        payment_button.setOnClickListener {
            viewModel.onClickPay()
        }

        payment_value.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onValueChanged(s.toString())
            }
        })
    }

    private fun bindPayee() {
        viewModel.payee.observe(this, Observer {
            payment_username.text = it.username
            loadPicture(it.img)
        })
    }

    private fun bindCard() {
        viewModel.card.observe(this, Observer {
            val cardEnding = it.number.substring(it.number.length - 4)
            payment_card.text = "X X X $cardEnding"
        })
    }

    private fun loadPicture(url: String) {
        payment_picture_progress_bar.visibility = View.VISIBLE
        Picasso.get()
                .load(url)
                .error(R.drawable.ic_round_account_circle)
                .into(payment_picture, object : Callback {
                    override fun onSuccess() {
                        payment_picture_progress_bar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        payment_picture_progress_bar.visibility = View.GONE
                    }
                })
    }

    private fun setEnabled(enabled: Boolean) {
        val colorId = if (enabled) R.color.colorAccent else R.color.colorDetail
        val color = ContextCompat.getColor(context!!, colorId)
        payment_currency.setTextColor(color)
        payment_value.setTextColor(color)

        payment_button.isEnabled = enabled
    }

    private fun setProcessing(isProcessing: Boolean) {

    }

    private fun onSuccess() {
        setEnabled(false)
    }

    private fun onError(message: StringResource) {
        setEnabled(true)
        Snackbar.make(fragment_payment_root, getString(message.id), Snackbar.LENGTH_LONG).show()
    }
}