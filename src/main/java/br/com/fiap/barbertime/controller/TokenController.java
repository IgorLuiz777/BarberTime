package br.com.fiap.barbertime.controller;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.barbertime.model.dto.LoginRequest;
import br.com.fiap.barbertime.model.dto.LoginResponse;
import br.com.fiap.barbertime.repository.BarbeariaRepository;
import br.com.fiap.barbertime.repository.UserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class TokenController {
    
    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BarbeariaRepository barbeariaRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {

        var user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        var now = Instant.now();
        var expiresIn = 14400L;

        var scopes = user.get().getRoles()
        .stream()
        .map(role -> role.getName().name())
        .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
        .issuer("mybackend")
        .subject(user.get().getId().toString())
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiresIn))
        .claim("scope", scopes)
        .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @PostMapping("/barbearia/login")
    public ResponseEntity<LoginResponse> loginBarbearia(@RequestBody LoginRequest loginRequest) {

        var barbearia = barbeariaRepository.findByEmail(loginRequest.email());

        if (barbearia.isEmpty() || !barbearia.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        var now = Instant.now();
        var expiresIn = 14400L;

        var scopes = barbearia.get().getRoles()
        .stream()
        .map(role -> role.getName().name())
        .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
        .issuer("mybackend")
        .subject(barbearia.get().getId().toString())
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiresIn))
        .claim("scope", scopes)
        .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

}
