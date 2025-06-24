package com.techelevator.movies.dao;

import com.techelevator.movies.model.Collection;
import com.techelevator.movies.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JdbcPersonDao implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPersonDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private Person getPerson(SqlRowSet result) {
        Person person = new Person();
        person.setId(result.getInt("person_id"));
        person.setName(result.getString("person_name"));
        java.sql.Date birthdayDate = result.getDate("birthday");
        if (birthdayDate != null) {
            person.setBirthday(birthdayDate.toLocalDate());
        }
        Date deathdayDate = result.getDate("deathday");
        if (deathdayDate != null) {
            person.setDeathDate(deathdayDate.toLocalDate());
        }
        person.setBiography(result.getString("biography"));
        person.setProfilePath(result.getString("profile_path"));
        person.setHomePage(result.getString("home_page"));
        return person;
    }
    @Override
    public List<Person> getPersons() {
        List<Person> output = new ArrayList<>();
        String sql = "select * from person;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()){
            output.add(getPerson(result));
        }
        return output;
    }

    @Override
    public Person getPersonById(int id) {
        Person output = null;
        String sql = "select * from person where person_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()){
            output = getPerson(result);
        }
        return output;
    }

    @Override
    public List<Person> getPersonsByName(String name, boolean useWildCard) {
        List<Person> output = new ArrayList<>();
        String sql = "select * from person where person_name ilike ?";
        if(useWildCard){
            name = "%"+name+"%";
        }
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);
        while(result.next()){
            output.add(getPerson(result));
        }
        return output;
    }

    @Override
    public List<Person> getPersonsByCollectionName(String collectionName, boolean useWildCard) {
        List<Person> output = new ArrayList<>();
        String sql = "select distinct person_id,\n" +
                "    p.person_name,\n" +
                "    p.birthday,\n" +
                "    p.deathday,\n" +
                "    p.biography,\n" +
                "    p.profile_path,\n" +
                "    p.home_page from person p\n" +
                "join movie_actor as ma on ma.actor_id = person_id\n" +
                "join movie on movie.movie_id = ma.movie_id\n" +
                "join collection as co on co.collection_id = movie.collection_id\n" +
                "where collection_name ilike ?\n" +
                "order by person_name;";
        if(useWildCard){
            collectionName = "%" + collectionName + "%";
        }
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, collectionName);
        while(result.next()){
            output.add(getPerson(result));
        }
        return output;
    }
}
