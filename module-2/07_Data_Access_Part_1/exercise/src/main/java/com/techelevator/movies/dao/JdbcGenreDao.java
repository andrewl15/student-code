package com.techelevator.movies.dao;

import com.techelevator.movies.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcGenreDao implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    private Genre getGenre(SqlRowSet result){
        Genre genre = new Genre();
        genre.setId(result.getInt("genre_id"));
        genre.setName(result.getString("genre_name"));
        return genre;
    }
    public JdbcGenreDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Genre> getGenres() {
        List<Genre> output = new ArrayList<>();
        String sql = "select * from genre;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()){
            output.add(getGenre(result));
        }
        return output;
    }

    @Override
    public Genre getGenreById(int id) {
        Genre output = null;
        String sql = "select * from genre where genre_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()){
            output = getGenre(result);
        }
        return output;
    }

    @Override
    public List<Genre> getGenresByName(String name, boolean useWildCard) {
        List<Genre> output = new ArrayList<>();
        String sql = "select * from genre where genre_name ilike ?;";
        if(useWildCard){
            name = "%" + name + "%";
        }
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);
        while(result.next()){
            output.add(getGenre(result));
        }
        return output;
    }
}
