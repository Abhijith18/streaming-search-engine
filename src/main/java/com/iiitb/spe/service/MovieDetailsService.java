package com.iiitb.spe.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import com.iiitb.spe.models.Movie_Details;
import com.iiitb.spe.repositories.MovieDetailsRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
//@ComponentScan("com.iiitb.spe.service")
public class MovieDetailsService {


    @Autowired
     MovieDetailsRepository repos;

    public Movie_Details findByMovieName(String movie_name)
    {
        return repos.findByMovieName(movie_name);
    }

    public List<Movie_Details> findbyMovieGenre(String movie_genre) {return repos.findByMovieGenre(movie_genre); }

    public void save(Movie_Details md) {repos.save(md);}
}
