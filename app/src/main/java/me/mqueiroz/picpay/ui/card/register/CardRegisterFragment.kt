package me.mqueiroz.picpay.ui.card.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_card_register.*
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.di.injector

class CardRegisterFragment : Fragment() {

    private val viewModel: CardRegisterViewModel by lazy {
        ViewModelProviders
            .of(this, injector.cardRegisterViewModelFactory())
            .get(CardRegisterViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}