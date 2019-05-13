package me.mqueiroz.picpay.ui.card.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_card_register.*
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.di.injector
import com.redmadrobot.inputmask.MaskedTextChangedListener
import androidx.annotation.NonNull
import androidx.navigation.fragment.navArgs
import me.mqueiroz.picpay.common.entities.Card


class CardRegisterFragment : Fragment() {

    private val args: CardRegisterFragmentArgs by navArgs()

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
            findNavController().navigateUp()
        }

        card_register_number_input.addMaskedTextWatcher("[0000] [0000] [0000] [0000]", viewModel::onCardNumberChanged)
        card_register_name_input.addTextWatcher(viewModel::onNameChanged)
        card_register_expiration_input.addMaskedTextWatcher("[00]{/}[00]", viewModel::onExpiryDateChanged)
        card_register_cvv_input.addMaskedTextWatcher("[000]", viewModel::onCVVChanged)

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is CardRegisterFragmentState.SaveDisabled -> onDisable()
                is CardRegisterFragmentState.SaveEnabled -> onEnable()
                is CardRegisterFragmentState.Success -> onSuccess(state.card)
                is CardRegisterFragmentState.Error -> onError()
            }
        })

        card_register_save_button.setOnClickListener {
            viewModel.onClickSave()
        }
    }

    private fun onEnable() {
        card_register_save_button.visibility = View.VISIBLE
    }

    private fun onDisable() {
        card_register_save_button.visibility = View.GONE
    }

    private fun onSuccess(card: Card) {
        val action = CardRegisterFragmentDirections
                .actionCardRegisterFragmentToPaymentFragment(args.user, card)
        findNavController().navigate(action)
    }

    private fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun EditText.addTextWatcher(listener: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listener(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun EditText.addMaskedTextWatcher(mask: String, listener: (String) -> Unit) {
        MaskedTextChangedListener.installOn(
                this,
                mask,
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(maskFilled: Boolean, @NonNull extractedValue: String, @NonNull formattedValue: String) {
                        listener(extractedValue)
                    }
                }
        )
    }
}