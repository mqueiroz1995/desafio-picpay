package me.mqueiroz.picpay.model.local

import io.reactivex.Completable
import io.reactivex.Maybe
import me.mqueiroz.picpay.common.entities.Card

interface CardStorage {

    fun get(): Maybe<Card>

    fun save(card: Card): Completable
}