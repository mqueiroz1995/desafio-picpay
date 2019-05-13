package me.mqueiroz.picpay.model.local

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import io.reactivex.Completable
import io.reactivex.Maybe
import me.mqueiroz.picpay.common.entities.Card
import javax.inject.Inject

class CardStorageImpl @Inject constructor(
        val sharedPreferences: SharedPreferences,
        val moshi: Moshi
) : CardStorage {

    private val CARD_KEY = "card"

    private val adapter = moshi.adapter(Card::class.java)

    override fun get(): Maybe<Card> {
        if (sharedPreferences.contains(CARD_KEY)) {
            val json = sharedPreferences.getString(CARD_KEY, "")!!
            val card = adapter.fromJson(json)
            return Maybe.just(card)
        } else {
            return Maybe.empty()
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun save(card: Card): Completable {
        sharedPreferences.edit()
                .putString(CARD_KEY, adapter.toJson(card))
                .commit()

        return Completable.complete()
    }
}