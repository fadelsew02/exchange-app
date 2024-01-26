package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.exchangeApp.model.Jwt;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValeurAndDesactiveAndExpire(String valeur, boolean desactive, boolean expire);

    @Query("SELECT j FROM Jwt j WHERE j.expire = :expire AND j.desactive = :desactive AND j.user.email = :email")
    Optional<Jwt> findUserValidToken(String email, boolean desactive, boolean expire);

    @Query("FROM Jwt j WHERE j.user.email = :email")
    Stream<Jwt> findUser(String email);

    @Query("FROM Jwt j WHERE j.refreshToken.valeur = :valeur")
    Optional<Jwt> findByRefreshToken(String valeur);

    void deleteAllByExpireAndDesactive(boolean expire, boolean desactive);
}