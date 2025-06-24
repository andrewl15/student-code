package com.techelevator.movies.dao;

import com.techelevator.movies.model.Genre;
import com.techelevator.movies.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcMovieDao implements MovieDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private Movie getMovie(SqlRowSet result){
        Movie movie = new Movie();
        movie.setId(result.getInt("movie_id"));
        movie.setTitle(result.getString("title"));
        movie.setOverview(result.getString("overview"));
        movie.setTagline(result.getString("tagline"));
        movie.setPosterPath(result.getString("poster_path"));
        movie.setHomePage(result.getString("home_page"));
        movie.setReleaseDate(result.getDate("release_date").toLocalDate());
        movie.setLengthMinutes(result.getInt("length_minutes"));
        movie.setDirectorId(result.getInt("director_id"));
        movie.setCollectionId(result.getInt("collection_id"));
        return movie;
    }

    @Override
    public List<Movie> getMovies() {
        List<Movie> output = new ArrayList<>();
        String sql = "select * from movie;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()){
            output.add(getMovie(result));
        }
        return output;
    }

    @Override
    public Movie getMovieById(int id) {
        Movie output = null;
        String sql = "select * from movie where movie_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()){
            output = getMovie(result);
        }
        return output;
    }

    @Override
    public List<Movie> getMoviesByTitle(String title, boolean useWildCard) {
        List<Movie> output = new ArrayList<>();
        String sql = "select * from movie where title ilike ?;";
        if(useWildCard){
            title = "%"+title+"%";
        }
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, title);
        while(result.next()){
            output.add(getMovie(result));
        }
        return output;
    }

    @Override
    public List<Movie> getMoviesByDirectorNameAndBetweenYears(String directorName, int startYear,
                                                              int endYear, boolean useWildCard) {
        List<Movie> output = new ArrayList<>();
        String sql = "select * from movie \n" +
                "join person p on p.person_id = movie.director_id\n" +
                "where p.person_name ilike ?\n" +
                "and movie.release_date between make_date(?, 1, 1) and make_date(?, 12, 31);";
        if(useWildCard){
            directorName = "%"+directorName+"%";
        }
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,directorName,startYear,endYear);
        while(result.next()){
            output.add(getMovie(result));
        }
        return output;
    }
}
