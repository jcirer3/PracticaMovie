package com.esliceu.movie.services;

import com.esliceu.movie.DAO.GenreDAO;
import com.esliceu.movie.DAO.MovieGenreDAO;
import com.esliceu.movie.models.Genre;
import com.esliceu.movie.models.MovieGenresId;
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
public class GenreService {
    @Autowired
    GenreDAO genreDAO;
    @Autowired
    MovieGenreDAO movieGenreDAO;

    public Page<Genre> getPaginatedGenres(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return genreDAO.findAll(pageable);
    }

    public String saveGenre(String genreName) {
        Genre existingGenre = genreDAO.findByGenreName(genreName);

        if (existingGenre != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar el nuevo genre
            Genre genre = new Genre();
            genre.setGenreName(genreName);
            genreDAO.save(genre);
            return null;
        }
    }

    public void deleteGenre(Integer genreId) {
        Genre genre = genreDAO.findById(genreId).get();
        if (genre != null) {
            genreDAO.delete(genre);
        }
    }

    public Genre getGenreById(Integer genreId) {
        return genreDAO.findById(genreId).get();
    }

    public String updateGenreNameById(Integer genreId, String genreName) {
        Genre existingGenre = genreDAO.findByGenreName(genreName);
        if (existingGenre != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<Genre> optionalGenre = genreDAO.findById(genreId);

        Genre genre = optionalGenre.get();
        genre.setGenreName(genreName);
        genreDAO.save(genre);

        return null;
    }

    public String getGenreJson() {
        List<Genre> genres = genreDAO.findAll();
        List<String> names = genres.stream()
                .map(g -> g.getGenreName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<Genre> findByName(String genreName) {
        List<Genre> genreList = new ArrayList<>();
        Genre genre = genreDAO.findByGenreName(genreName);
        genreList.add(genre);
        return genreList;
    }

    public void deleteMovieGenre(MovieGenresId movieGenresId) {
        movieGenreDAO.deleteById(movieGenresId);
    }

    public Genre findByGenreName(String genreName) {
        return genreDAO.findByGenreName(genreName);
    }
}
