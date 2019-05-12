package me.mqueiroz.picpay.ui.payment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.toolbar
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.di.injector

class PaymentFragment : Fragment() {

    private val viewModel: PaymentViewModel by lazy {
        ViewModelProviders
                .of(this, injector.paymentViewModelFactory())
                .get(PaymentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        payment_username.text ="@mqueiroz"
        payment_currency.text = "R$"
        payment_card.text = "Mastercard 1234"
    }
}