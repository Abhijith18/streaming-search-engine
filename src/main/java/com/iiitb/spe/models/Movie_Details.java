package com.iiitb.spe.models;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iiitb.spe.clientmodels.Movie;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.sql.Date;
@Entity
@Table(name = "movie_details")
public class Movie_Details {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(name = "movie_name")
    private String movie_name;
    @Column(name = "release_date")
    private String release_date;
    @Column(name = "ott_platforms")
    private String ott_platforms;

    @Column(name = "genre")
    private String genre;

    @Column(name = "movie_img")
    private String movie_img;

    @Column(name = "about")
    private String about;

    public Movie_Details(String movie_name, String release_date, String ott_platforms, String genre, String movie_img, String about) {
        this.movie_name = movie_name;
        this.release_date = release_date;
        this.ott_platforms = ott_platforms;
        this.genre = genre;
        this.movie_img = movie_img;
        this.about = about;
    }

    public Movie_Details(){}

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_img() {
        return movie_img;
    }

    public void setMovie_img(String movie_img) {
        this.movie_img = movie_img;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOtt_platforms() {
        return ott_platforms;
    }

    public void setOtt_platforms(String ott_platforms) {
        this.ott_platforms = ott_platforms;
    }
//    public String getAllDetails(){
//        return "Movie is " + this.movie_name + ".Release date is "  + this.release_date + ".ott-platfroms "+this.ott_platforms;
//    }
}

