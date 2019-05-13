package me.mqueiroz.picpay.ui.card.priming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_card_register_priming.*
import me.mqueiroz.picpay.R

class CardRegisterPrimingFragment : Fragment() {

    private val args: CardRegisterPrimingFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card_register_priming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        card_register_priming_button.setOnClickListener {
            val action = CardRegisterPrimingFragmentDirections
                    .actionCardRegisterPrimingFragmentToCardRegisterFragment(args.user)
            findNavController().navigate(action)
        }
    }
}