package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.AccountDAO;
import com.techelevator.tebucks.dao.TransferDAO;
import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.NewTransferDTO;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.TransferStatusUpdateDTO;
import com.techelevator.tebucks.dao.UserDao;
import com.techelevator.tebucks.model.TxLogDTO;
import com.techelevator.tebucks.model.User;
import com.techelevator.tebucks.services.ExternalLog;
import com.techelevator.tebucks.services.Util;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private UserDao userDao;
    private AccountDAO accountDAO;
    private TransferDAO transferDAO;
    private ExternalLog tears;
    public TransferController(UserDao userDao, AccountDAO accountDAO, TransferDAO transferDAO, ExternalLog tears) {
        this.userDao = userDao;
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
        this.tears = tears;
    }

    @GetMapping
    public Transfer GetTransfer(@PathVariable int id){
        return transferDAO.GetTransferByID(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transfer CreateTransfer(@RequestBody NewTransferDTO transferIn, Principal principal){
        int currentUserID = Util.getCurrentUserId(principal, userDao);
        if(transferIn.getTransferType().toLowerCase().equals("send")) {
            if (transferIn.getUserTo() == currentUserID) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot transfer money to yourself.");
            }
            try{
                Account usersAccount = accountDAO.GetAccountByUserID(currentUserID);
                if (transferIn.getAmount() > usersAccount.getBalance().doubleValue()) {
                    // log with TEARS -- Overdraft
                    tears.logItem(createTxLogDTOFromTx(transferIn,"Overdraft issue."));
                    throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED,"Insufficient Funds");
                }
            }catch(DaoException d){
               throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        if(transferIn.getAmount() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Transfer amount must be positive.");
        }
        try{
            Transfer output = transferDAO.CreateTransfer(transferIn);
            if(transferIn.getAmount() >= 1000){
                // log with TEARS -- Over a Grand
                tears.logItem(createTxLogDTOFromTx(transferIn,"Just noting this is over $1,000"));
            }
            return output;
        } catch(DaoException d){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("{id}/status")
    public Transfer UpdateTransfer(@RequestBody TransferStatusUpdateDTO dto, @PathVariable int id, Principal principal){
        try{
            Transfer transferDetails = transferDAO.GetTransferByID(id);
            int userId = Util.getCurrentUserId(principal,userDao);
            Account account = accountDAO.GetAccountByUserID(userId);
            if(transferDetails == null || account == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            if(transferDetails.getUserFrom().getId() != userId){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            if(transferDetails.getUserFrom().getId() == userId &&
                    (account.getBalance().compareTo(transferDetails.getAmount()) < 0 &&
                    dto.getTransferStatus().toLowerCase().equals("approved"))){
                throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED,"Insufficient Funds");
            }
            // If here, all is good
            transferDetails.setTransferStatus((dto.getTransferStatus()));
            return transferDAO.UpdateTransferStatus(transferDetails);

        } catch (DaoException e){
            String foo = e.getMessage();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private TxLogDTO createTxLogDTOFromTx(NewTransferDTO dataIn, String reason){
        User userFrom = userDao.getUserByID(dataIn.getUserFrom());
        User userTo = userDao.getUserByID(dataIn.getUserTo());
        TxLogDTO tx = new TxLogDTO();
        tx.setDescription(reason);
        tx.setUsername_from(userFrom.getUsername());
        tx.setUsername_to(userTo.getUsername());
        tx.setAmount(dataIn.getAmount());
        return tx;
    }
}
