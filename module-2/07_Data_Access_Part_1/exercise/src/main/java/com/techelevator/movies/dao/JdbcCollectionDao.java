package com.techelevator.movies.dao;

import com.techelevator.movies.model.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCollectionDao implements CollectionDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private Collection getCollection(SqlRowSet result) {
        Collection collection = new Collection();
        collection.setId(result.getInt("collection_id"));
        collection.setName(result.getString("collection_name"));
        return collection;
    }

    @Override
    public List<Collection> getCollections() {
        List<Collection> output = new ArrayList<>();
        String sql = "select * from collection;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()){
            output.add(getCollection(result));
        }
        return output;
    }

    @Override
    public Collection getCollectionById(int id) {
        Collection output = null;
        String sql = "select * from collection where collection_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()){
            output = getCollection(result);
        }
        return output;
    }

    @Override
    public List<Collection> getCollectionsByName(String name, boolean useWildCard) {
        List<Collection> output = new ArrayList<>();
        String sql = "select * from collection where collection_name ilike ?";
        if(useWildCard){
            name = "%" + name + "%";
        }
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,name);
        while(result.next()){
            output.add(getCollection(result));
        }
        return output;
    }
}
