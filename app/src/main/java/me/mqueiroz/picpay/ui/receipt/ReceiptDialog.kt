package me.mqueiroz.picpay.ui.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_receipt.*
import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.common.entities.Card
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class ReceiptDialog : BottomSheetDialogFragment() {

    private val args: ReceiptDialogArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return inflater.inflate(R.layout.dialog_receipt, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCard(args.card)

        args.receipt.let { receipt ->
            loadPicture(receipt.destinationUser.img)
            setDate(receipt.timestamp)
            receipt_username.text = receipt.destinationUser.username
            receipt_id.text = getString(R.string.receipt_id, receipt.id)
            receipt_partial_value.text = getString(R.string.receipt_money, receipt.value)
            receipt_total_value.text = getString(R.string.receipt_money, receipt.value)
        }
    }

    private fun loadPicture(url: String) {
        receipt_progress_bar.visibility = View.VISIBLE
        Picasso.get()
                .load(url)
                .error(R.drawable.ic_round_account_circle)
                .into(receipt_image, object : Callback {
                    override fun onSuccess() {
                        receipt_progress_bar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        receipt_progress_bar.visibility = View.GONE
                    }
                })
    }

    private fun setCard(card: Card) {
        val cardEnding = card.number.substring(card.number.length - 4)
        receipt_card.text = getString(R.string.receipt_credit_card, cardEnding)
    }

    private fun setDate(timestamp: Long) {
        val sdf = SimpleDateFormat("dd/MM/yyyy às HH:mm")
        val netDate = Date(timestamp)
        receipt_date.text = sdf.format(netDate)
    }
}