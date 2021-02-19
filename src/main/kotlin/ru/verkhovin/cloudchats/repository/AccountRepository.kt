package ru.verkhovin.cloudchats.repository

import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.springframework.stereotype.Component
import ru.verkhovin.cloudchats.exception.notFoundException
import ru.verkhovin.cloudchats.model.data.Account

@Component
class AccountRepository(
  accountCollection: MongoCollection<Account>
) : Repository<Account>(accountCollection) {
  fun findByEmail(email: String) =
    collection.findOne(Account::email eq email)
      ?: throw Account::class.notFoundException(Account::email, email)

  fun findByRefreshToken(refreshToken: String) =
    collection.findOne(Account::refreshToken eq refreshToken)
      ?: throw Account::class.notFoundException(Account::refreshToken, refreshToken)
}
