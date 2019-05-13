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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.toolbar
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.common.entities.Receipt
import me.mqueiroz.picpay.di.injector
import java.lang.Exception
import me.mqueiroz.picpay.utils.CurrencyTextWatcher
import me.mqueiroz.picpay.utils.StringResource


class PaymentFragment : Fragment() {

    private val args: PaymentFragmentArgs by navArgs()

    private val viewModel: PaymentViewModel by lazy {
        ViewModelProviders
                .of(this, injector.paymentViewModelFactory())
                .get(PaymentViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        val user = args.user
        val card = args.card
        viewModel.setState(user, card)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
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
                is PaymentFragmentState.PaymentSuccess -> onSuccess(state.receipt)
                is PaymentFragmentState.PaymentError -> onError(state.message)
            }
        })

        payment_edit_card_button.setOnClickListener {
            navigateToCardRegisterScreen()
        }

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
            payment_card.text = getString(R.string.payment_credit_card, cardEnding)
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

    private fun onSuccess(receipt: Receipt) {
        navigateToHomeScreen(receipt)
    }

    private fun onError(message: StringResource) {
        Snackbar.make(fragment_payment_root, getString(message.id), Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToHomeScreen(receipt: Receipt) {
        val action = PaymentFragmentDirections
                .actionPaymentFragmentToHomeFragment(receipt)
        findNavController().navigate(action)
    }

    private fun navigateToCardRegisterScreen() {
        val action = PaymentFragmentDirections
                .actionPaymentFragmentToCardRegisterFragment(args.user, args.card)
        findNavController().navigate(action)
    }
}