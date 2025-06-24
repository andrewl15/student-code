package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.AccountDAO;
import com.techelevator.tebucks.dao.TransferDAO;
import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.dao.UserDao;
import com.techelevator.tebucks.services.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@PreAuthorize("isAuthenticated()")

public class AccountController {
    private final AccountDAO accountDAO;
    private final UserDao userDao;
    private final TransferDAO transferDAO;

    public AccountController(AccountDAO accountDAO, UserDao userDao, TransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.userDao = userDao;
        this.transferDAO = transferDAO;
    }

    @GetMapping("balance")
    public Account GetAccount(Principal principal) {
        try{
            return accountDAO.GetAccountByUserID(Util.getCurrentUserId(principal, userDao));
        } catch (DaoException d){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("transfers")
    public List<Transfer> GetTransfers(Principal principal) {
        try{
            return transferDAO.GetTransfersByUserId(Util.getCurrentUserId(principal, userDao));
        } catch (DaoException d){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("teapot")
    @PreAuthorize("permitAll")
    public void ImATeapot(){
        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT,"Don't ask me, I'm a teapot.");
    }

}
