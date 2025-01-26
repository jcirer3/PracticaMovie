package com.esliceu.movie.services;

import com.esliceu.movie.DAO.KeywordDAO;
import com.esliceu.movie.models.Keyword;
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
public class KeywordService {
    @Autowired
    KeywordDAO keywordDAO;

    public Page<Keyword> getPaginatedKeywords(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return keywordDAO.findAll(pageable);
    }

    public String saveKeyword(String keywordName) {
        Keyword existingKeyword = keywordDAO.findByKeywordName(keywordName);

        if (existingKeyword != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar la nueva keyword
            Keyword keyword = new Keyword();
            keyword.setKeywordName(keywordName);
            keywordDAO.save(keyword);
            return null;
        }
    }

    public void deleteKeyword(Integer keywordId) {
        Keyword keyword = keywordDAO.findById(keywordId).get();
        if (keyword != null) {
            keywordDAO.delete(keyword);
        }
    }

    public Keyword getKeywordById(Integer keywordId) {
        return keywordDAO.findById(keywordId).get();
    }

    public String updateKeywordNameById(Integer keywordId, String keywordName) {
        Keyword existingKeyword = keywordDAO.findByKeywordName(keywordName);
        if (existingKeyword != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<Keyword> optionalKeyword = keywordDAO.findById(keywordId);

        Keyword keyword = optionalKeyword.get();
        keyword.setKeywordName(keywordName);
        keywordDAO.save(keyword);

        return null;
    }

    public String getKeywordJson() {
        List<Keyword> keywords = keywordDAO.findAll();
        List<String> names = keywords.stream()
                .map(k -> k.getKeywordName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<Keyword> findByName(String keywordName) {
        List<Keyword> keywordList = new ArrayList<>();
        Keyword keyword = keywordDAO.findByKeywordName(keywordName);
        keywordList.add(keyword);
        return keywordList;
    }

    public Integer getKeywordIdByName(String keywordName) {
        Keyword keyword = keywordDAO.findByKeywordName(keywordName);
        return keyword.getKeywordId();
    }
}
