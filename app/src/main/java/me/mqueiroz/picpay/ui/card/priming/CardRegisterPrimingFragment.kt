package me.mqueiroz.picpay.ui.card.priming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_card_register_priming.*
import me.mqueiroz.picpay.R

class CardRegisterPrimingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card_register_priming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        card_register_priming_button.setOnClickListener {
            findNavController().navigate(R.id.action_cardRegisterPrimingFragment_to_cardRegisterFragment)
        }
    }
}