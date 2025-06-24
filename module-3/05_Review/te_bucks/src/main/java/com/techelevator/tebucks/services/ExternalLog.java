package com.techelevator.tebucks.services;

import com.techelevator.tebucks.model.*;

public interface ExternalLog {
    TxLog logItem(TxLogDTO dataIn);
}
