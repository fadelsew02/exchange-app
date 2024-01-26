package com.example.exchangeApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.exchangeApp.model.Jwt;
import com.example.exchangeApp.model.RefreshToken;
import com.example.exchangeApp.model.User;
import com.example.exchangeApp.repo.JwtRepository;
import com.example.exchangeApp.service.userService;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class JwtService {
    public static final String BEARER = "bearer";
    public static final String REFRESH = "refresh";
    public static final String TOKEN_INVALIDE = "Token invalide";
    private final String ENCRIPTION_KEY = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
    private userService userService;
    private JwtRepository jwtRepository;

    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValeurAndDesactiveAndExpire(
                value,
                false,
                false
        ).orElseThrow(() -> new RuntimeException("Token invalide ou inconnu"));
    }
    public Map<String, String> generate(String username) {
        User User = this.userService.loadUserByUsername(username);
        this.disableTokens(User);
        final Map<String, String> jwtMap = new java.util.HashMap<>(this.generateJwt(User));

        RefreshToken refreshToken = RefreshToken.builder()
                .valeur(UUID.randomUUID().toString())
                .expire(false)
                .creation(Instant.now())
                .expiration(Instant.now().plusMillis(30 *60 *1000))
                .build();

        final Jwt jwt = Jwt
                .builder()
                .valeur(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .user(User)
                .refreshToken(refreshToken)
                .build();

        this.jwtRepository.save(jwt);
        jwtMap.put(REFRESH,  refreshToken.getValeur());
        return jwtMap;
    }

    private void disableTokens(User user) {
        final List<Jwt> jwtList = this.jwtRepository.findUser(user.getEmail()).peek(
                jwt -> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                }
        ).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwtList);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "nom", user.getFirstName(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, user.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public void deconnexion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            Jwt jwt = this.jwtRepository.findUserValidToken(
                loggedInUser.getEmail(),
                false,
                false
            ).orElseThrow(() -> new RuntimeException(TOKEN_INVALIDE));

            jwt.setExpire(true);
            jwt.setDesactive(true);

            this.jwtRepository.save(jwt);
        }
    }


    @Scheduled(cron = "@daily")
    public void removeUselessJwt() {
        log.info("Suppression des token à {}", Instant.now());
        this.jwtRepository.deleteAllByExpireAndDesactive(true, true);
    }

    public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest) {
        final Jwt jwt = this.jwtRepository.findByRefreshToken(refreshTokenRequest.get(REFRESH)).orElseThrow(() -> new RuntimeException(TOKEN_INVALIDE));
        if(jwt.getRefreshToken().isExpire() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())) {
            throw new RuntimeException(TOKEN_INVALIDE);
        }
        this.disableTokens(jwt.getUser());
        return this.generate(jwt.getUser().getEmail());
    }
}