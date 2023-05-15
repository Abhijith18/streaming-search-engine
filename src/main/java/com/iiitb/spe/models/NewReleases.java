package com.iiitb.spe.models;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.sql.Date;
@Entity
@Table(name = "new_releases")
public class NewReleases {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(name = "phone_number")
    private String phone_number;

    public int getId() {
        return id;
    }

    public NewReleases(String movie_name, String phone_number) {
        this.movie_name = movie_name;
        this.phone_number = phone_number;
    }
    public NewReleases() {
        // default constructor
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }


    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    @Column(name = "movie_name")
    private String movie_name;
}
