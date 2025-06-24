package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDTO;
import com.techelevator.tebucks.model.Transfer;

import java.util.List;

public interface TransferDAO {
    Transfer CreateTransfer(NewTransferDTO dataIn);
    Transfer GetTransferByID(int transferId);
    Transfer UpdateTransferStatus(Transfer transfer);
    List<Transfer> GetTransfersByUserId(int userId);
}
