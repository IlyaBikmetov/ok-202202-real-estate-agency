package ru.ibikmetov.kotlin.realestateagency.spring.config.jwt

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgPrincipalModel
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgUserGroups
import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgUserId

@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return UserDetailsImpl.build()
    }

    fun createUserByJWT(jwtUtils: JwtUtils, jwt: String): ReAgPrincipalModel {
        val id = jwtUtils.getClaimsFromJwtToken(jwt, "id")
        val fName = jwtUtils.getClaimsFromJwtToken(jwt, "fname")
        val mName = jwtUtils.getClaimsFromJwtToken(jwt, "mname")
        val lName = jwtUtils.getClaimsFromJwtToken(jwt, "lname")
        val groups = jwtUtils.getClaimsFromJwtToken(jwt, "groups")
            .replace("[", "")
            .replace("]", "")
            .replace(" ", "")
            .split(',')
            .map { ReAgUserGroups.valueOf(it) }.toSet()
        return ReAgPrincipalModel(ReAgUserId(id),
            fName,
            mName,
            lName,
            groups
        )
    }
}