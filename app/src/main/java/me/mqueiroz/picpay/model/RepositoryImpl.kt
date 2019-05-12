package me.mqueiroz.picpay.model

import io.reactivex.Observable
import me.mqueiroz.picpay.common.entities.User
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {

    override fun getUsers(): Observable<List<User>> {
        return Observable.just(
            listOf(
                User("User1"),
                User("User2"),
                User("User3"),
                User("User4"),
                User("User5")
            )
        )
    }
}