package ru.ibikmetov.kotlin.realestateagency.spring.config.jwt

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtUtils {
    @Value("\${jwt.secret}")
    private val jwtSecret: String? = null

    @Value("\${jwt.issuer}")
    private val jwtIssuer: String? = null

    @Value("\${jwt.audience}")
    private val jwtAudience: String? = null

    fun getClaimsFromJwtToken(token: String?, key: String): String {
        var claim = ""
        try {
            val claim = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body[key]
                .toString()
            return claim
        } catch (e: ClaimJwtException) {
            logger.error("Invalid JWT claim: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        } catch (e: JwtException) {
            logger.error("JWT exception: {}", e.message)
        }
        return claim
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser()
                .setSigningKey(jwtSecret)
                .requireAudience(jwtAudience)
                .requireIssuer(jwtIssuer)
                .parse(authToken)
            logger.info("JWT valid")
            return true
        } catch (e: SignatureException) {
            logger.warn("Invalid JWT signature: {}", e.message)
        } catch (e: SecurityException) {
            logger.warn("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.warn("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.warn("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.warn("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.warn("JWT claims string is empty: {}", e.message)
        }
        return false
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtUtils::class.java)
    }
}