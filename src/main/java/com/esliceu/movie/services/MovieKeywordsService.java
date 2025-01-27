package com.esliceu.movie.services;

import com.esliceu.movie.DAO.MovieKeywordDAO;
import com.esliceu.movie.DAO.PersonDAO;
import com.esliceu.movie.models.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieKeywordsService {
    @Autowired
    MovieKeywordDAO movieKeywordDAO;


    public void save(Movie movie, Keyword keyword) {
        MovieKeywords movieKeyword = new MovieKeywords();
        MovieKeywordsId id = new MovieKeywordsId(movie.getMovieId(), keyword.getKeywordId());
        movieKeyword.setId(id);
        movieKeyword.setMovie(movie);
        movieKeyword.setKeyword(keyword);
        movieKeywordDAO.save(movieKeyword);
    }
}
