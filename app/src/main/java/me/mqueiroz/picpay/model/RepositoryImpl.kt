package me.mqueiroz.picpay.model

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.common.entities.Transaction
import me.mqueiroz.picpay.common.entities.Receipt
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.model.local.CardStorage
import me.mqueiroz.picpay.model.network.PicPayService
import me.mqueiroz.picpay.model.network.PostTransactionRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
        private val picPayService: PicPayService,
        private val cardStorage: CardStorage
) : Repository {

    override fun getUsers(): Observable<List<User>> {
        return picPayService.getUsers().toObservable()
    }

    override fun getCard(): Maybe<Card> {
        return cardStorage.get()
    }

    override fun saveCard(card: Card): Completable {
        return cardStorage.save(card)
    }

    override fun saveTransaction(transaction: Transaction): Single<Receipt> {
        val requestBody = PostTransactionRequestBody.fromTransaction(transaction)
        return picPayService.postTransaction(requestBody)
                .map { response -> response.receipt }
    }
}