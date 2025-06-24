package com.techelevator.favoritemovieserver.controller;

import com.techelevator.favoritemovieserver.model.LoginDto;
import com.techelevator.favoritemovieserver.model.LoginResponseDto;
import com.techelevator.favoritemovieserver.model.User;
import jakarta.validation.Valid;

import com.techelevator.favoritemovieserver.exception.DaoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.techelevator.favoritemovieserver.dao.UserDao;
import com.techelevator.favoritemovieserver.security.jwt.JWTFilter;
import com.techelevator.favoritemovieserver.security.jwt.TokenProvider;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.createToken(authentication, false);

			User user;
            user = userDao.getUserByUsername(loginDto.getUsername());

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
			return new ResponseEntity<>(new LoginResponseDto(jwt, user), httpHeaders, HttpStatus.OK);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect.");
        }
    }

}

