package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDTO;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class jdbcTransferDAO implements TransferDAO {
    private final JdbcTemplate jdbcTemplate;
    private Map<String, Integer> TransferTypes = new HashMap<>();
    private Map<String, Integer> TransferStatuses = new HashMap<>();
    private String BaseSqlTranferData = "select transfer_id,amount,transfer_status.description as Status,transfer_type.description as Type, " +
            "userTo.first_name as To_FirstName,userTo.last_name as To_LastName,userTo.username as To_UserName,userTo.user_id as To_id, " +
            "userFrom.first_name as From_FirstName,userFrom.last_name as From_LastName,userFrom.username as From_UserName,userFrom.user_id as From_id " +
            "from transfer " +
            "join users as userTo on userTo.user_id = user_to_id " +
            "join users as userFrom on userFrom.user_id = user_from_id " +
            "join transfer_status on transfer_status.transferStatus_id = transfer.transferStatus_id " +
            "join transfer_type on transfer_type.transferType_id = transfer.transferType_id";

    public jdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        try{
            SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT * from transfer_type");
            while(rs.next()){
                if(!TransferTypes.containsKey(rs.getString("description").toLowerCase())){
                    TransferTypes.put(rs.getString("description").toLowerCase(),rs.getInt("transfertype_id"));
                }
            }

            rs = jdbcTemplate.queryForRowSet("SELECT * from transfer_status");
            while(rs.next()){
                if(!TransferStatuses.containsKey(rs.getString("description").toLowerCase())){
                    TransferStatuses.put(rs.getString("description").toLowerCase(),rs.getInt("transferstatus_id"));
                }
            }

        } catch (DataAccessException e){}
    }

    @Override
    public Transfer CreateTransfer(NewTransferDTO dataIn) {
        int newTransferID = 0;
        try{
            String sql = "INSERT INTO transfer (transferType_id,transferStatus_id,user_to_id,user_from_id,amount) " +
                    "VALUES (?,?,?,?,?) RETURNING transfer_id";
            // Check if send
            boolean isSend = dataIn.getTransferType().toLowerCase().trim().equals("send");
            String transferStatus = isSend ? "approved" : "pending";

            newTransferID = jdbcTemplate.queryForObject(sql,int.class,TransferTypes.get(dataIn.getTransferType().toLowerCase()),
                    TransferStatuses.get(transferStatus),
                    dataIn.getUserTo(),
                    dataIn.getUserFrom(),
                    dataIn.getAmount());

            if(isSend){
                TransferFunds(dataIn.getAmount(), dataIn.getUserFrom(), dataIn.getUserTo());
            }
        } catch (DataAccessException e){
            String foo = e.getMessage();
        }

        return GetTransferByID(newTransferID);
    }

    @Override
    public Transfer GetTransferByID(int transferId) {
        String sql = BaseSqlTranferData + " where transfer.transfer_id = ?;";
        Transfer output = null;
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql,transferId);
            if(result.next()){
                output = BaseReaderToTransfer(result);
            }
        } catch (DataAccessException e){

        }
        return output;
    }

    @Override
    public Transfer UpdateTransferStatus(Transfer transfer) {
        String sql = "Update transfer set transferStatus_id = ? where transfer_id = ?";
        try{
            int rowsAffected = jdbcTemplate.update(sql,
                    TransferStatuses.get(transfer.getTransferStatus().toLowerCase()),
                    transfer.getTransferId());
            if(rowsAffected != 1){
                // TODO Throw error
            }
            if(transfer.getTransferStatus().toLowerCase().equals("approved")){
                TransferFunds(transfer.getAmount().doubleValue(),transfer.getUserFrom().getId(),transfer.getUserTo().getId());
            }
        } catch(DataAccessException e){
            String foo = e.getMessage();
        }
        return GetTransferByID(transfer.getTransferId());
    }

    @Override
    public List<Transfer> GetTransfersByUserId(int userId) {
        List<Transfer> output = new ArrayList<>();
        String sql = BaseSqlTranferData + " where transfer.user_to_id = ? or transfer.user_from_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId,userId);
            while(results.next()){
                Transfer tempTransfer = BaseReaderToTransfer(results);
                output.add(tempTransfer);
            }
        } catch (DataAccessException e){
            String foo = e.getMessage();
        }
        return output;
    }

    private Transfer BaseReaderToTransfer(SqlRowSet reader)
    {
        Transfer temp = new Transfer();
        temp.setTransferId(reader.getInt("transfer_id"));
        temp.setTransferStatus(reader.getString("Status"));
        temp.setTransferType(reader.getString("Type"));
        Double amountValue = reader.getDouble("amount");
        temp.setAmount(new BigDecimal(amountValue));
        // now, the user info
        User tempUser = new User();
        tempUser.setId(reader.getInt("To_Id"));
        tempUser.setFirstName(reader.getString("To_FirstName"));
        tempUser.setLastName(reader.getString("To_LastName"));
        tempUser.setUsername(reader.getString("To_UserName"));
        temp.setUserTo(tempUser);

        tempUser = new User();
        tempUser.setId(reader.getInt("From_Id"));
        tempUser.setFirstName(reader.getString("From_FirstName"));
        tempUser.setLastName(reader.getString("From_LastName"));
        tempUser.setUsername(reader.getString("From_UserName"));
        temp.setUserFrom(tempUser);

        return temp;
    }
    private void TransferFunds(double amount, int userFrom, int userTo){
        String sql = "UPDATE account SET balance = (balance - ?) WHERE account_id = (SELECT account_id FROM account WHERE user_id = ? limit 1); " +
                "UPDATE account SET balance = (balance + ?) WHERE account_id = (SELECT account_id FROM account WHERE user_id = ? limit 1);";
        try {
            jdbcTemplate.update(sql,amount,userFrom,amount,userTo);
        } catch(DataAccessException e){
            String foo = e.getMessage();
        }
    }
}
