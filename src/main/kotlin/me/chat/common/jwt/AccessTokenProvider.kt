package me.chat.common.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import me.chat.user.domain.entity.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccessTokenProvider {
    @Value("\${security.jwt.token.secret-key}")
    private val secretKey: String? = null

    @Value("\${security.jwt.token.expire-length}")
    private val validityInMilliseconds: Long = 0

    fun create(user: User): String {
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .claim(USER_ID_CLAIM_KEY, user.id)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getClaimId(token: String): String {
        val claims: Claims = getPayload(token)

        try {
            if (claims.expiration.before(Date())) {
                throw IllegalArgumentException()
            }
        } catch (exception: Exception) {
            throw IllegalArgumentException()
        }

        return claims.id
    }

    private fun getPayload(token: String): Claims {
        try {
            return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
        } catch (exception: Exception) {
            throw IllegalArgumentException()
        }
    }

    companion object {
        private const val USER_ID_CLAIM_KEY = "id"
    }
}
