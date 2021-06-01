package com.sss.service.jwt

import com.sss.constant.DateTime
import com.sss.domain.entity.User
import com.sss.domain.repository.UserRepo
import com.sss.enum.JwtAuth
import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec


@Service
class JwtServiceImpl : JwtService {
    @Autowired
    private lateinit var userRepo: UserRepo;

    @Value("\${auth.access}")
    private val accessKey : String? = null;

    @Value ("\${auth.refresh}")
    private val refreshKey : String? = null;

    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    /**
     * 토큰 생성
     * @return Token
     */
    override fun createToken(idx: Long, authType: JwtAuth): String {
        var expiredAt: Date = Date()
        var secretKey: String? = null

        secretKey = when(authType) {
            JwtAuth.ACCESS -> {
                expiredAt = Date(expiredAt.time + DateTime().HOUR * 1)
                accessKey
            }
            JwtAuth.REFRESH -> {
                expiredAt = Date(expiredAt.time + DateTime().HOUR * 24 * 7)
                refreshKey
            }
            else -> {
                throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
            }
        }

        val signInKey: Key = SecretKeySpec(secretKey!!.toByteArray(), signatureAlgorithm.jcaName)

        val map: MutableMap<String, Any> = HashMap()
        map["idx"] = idx
        map["authType"] = authType

        val builder: JwtBuilder = Jwts.builder()
            .setClaims(map)
            .setExpiration(expiredAt)
            .signWith(signInKey)

        return builder.compact()
    }

    /**
     * 토큰 검증
     * @return User
     */
    override fun validateToken(token: String?): User? {
        try {
            val signingKey: Key = SecretKeySpec(accessKey!!.toByteArray(), signatureAlgorithm.jcaName)
            val claims: Claims = Jwts.parserBuilder().setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .body

            if (claims["authType"].toString() != "ACCESS") {
                throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 타입이 아님.")
            }

            return userRepo.findByIdx(claims["idx"].toString().toLong())
                ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "유저 없음.")

        } catch (e: ExpiredJwtException) {
            throw HttpClientErrorException(HttpStatus.GONE, "토큰 만료.")
        } catch (e: SignatureException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: MalformedJwtException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: IllegalArgumentException) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "토큰 없음.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }

    /**
     * 토큰 갱신
     * @return refresh token
     */
    override fun refreshToken(refreshToken: String?): String? {
        try {
            if (refreshToken == null || refreshToken.trim().isEmpty()) {
                throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
            }

            val signingKey: Key = SecretKeySpec(refreshToken.toByteArray(), signatureAlgorithm.jcaName)
            val claims: Claims = Jwts.parserBuilder().setSigningKey(signingKey)
                .build()
                .parseClaimsJws(refreshToken)
                .body

            if (claims["authType"].toString() != "REFRESH") {
                throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 타입이 아님.")
            }

            val user: User = userRepo.findByIdx(claims["idx"].toString().toLong())
                ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "유저 없음.")

            return createToken(user.idx!!, JwtAuth.ACCESS)
        } catch (e: ExpiredJwtException) {
            throw HttpClientErrorException(HttpStatus.GONE, "토큰 만료.")
        } catch (e: SignatureException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: MalformedJwtException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조.")
        } catch (e: IllegalArgumentException) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "토큰 없음.")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.")
        }
    }
}