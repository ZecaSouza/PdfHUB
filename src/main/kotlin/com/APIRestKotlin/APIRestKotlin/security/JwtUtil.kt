package com.APIRestKotlin.APIRestKotlin.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {

    private val secretKey = "yourSecretKey" //trocar

    fun generateToken(username: String): String{
        val claims = Jwts.claims().setSubject(username)
        val now = Date()
        val validity = Date(now.time + 3600000) // 1 HORA

        return Jwts.builder().setClaims(claims).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, secretKey.toByteArray()).compact()
    }

    fun validateToken(token: String): Boolean{
        return try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (e: Exception){
            false
        }
    }

    fun getUsername(token: String): String?{
        return getClaims(token).subject
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secretKey.toByteArray())
            .parseClaimsJws(token)
            .body
    }
}