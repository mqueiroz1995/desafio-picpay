package me.mqueiroz.picpay.model

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.common.entities.Receipt
import me.mqueiroz.picpay.common.entities.Transaction
import me.mqueiroz.picpay.common.entities.User

interface Repository {

    fun getUsers(): Observable<List<User>>

    fun getCard(): Maybe<Card>

    fun saveCard(card: Card): Completable

    fun saveTransaction(transaction: Transaction): Single<Receipt>
}