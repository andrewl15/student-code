package com.techelevator.tebucks.model;

import com.techelevator.tebucks.services.ExternalLog;

import java.util.Date;

public class TxLog  {
    private int log_id;
    private Date createdDate;

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
