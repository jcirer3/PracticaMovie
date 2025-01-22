package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Keyword;
import com.esliceu.movie.services.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class KeywordController {
    @Autowired
    KeywordService keywordService;

    @GetMapping("/keywords")
    public String listKeywords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<Keyword> keywordPage = keywordService.getPaginatedKeywords(page, size);

        String jsonToSend = keywordService.getKeywordJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("keywords", keywordPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", keywordPage.getTotalPages());
        model.addAttribute("totalItems", keywordPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "keywords";
    }

    @PostMapping("/keywords")
    public String searchKeyword(@RequestParam String keywordName, Model model){
        List<Keyword> keywords = keywordService.findByName(keywordName);
        model.addAttribute("keywords", keywords);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", keywords.size());
        return "keywords";
    }

    @PostMapping("/add-keyword")
    public String newKeyword(@RequestParam String keywordName, Model model) {
        String message = keywordService.saveKeyword(keywordName);

        if (message != null){
            List<Keyword> keywords = keywordService.findByName(keywordName);
            model.addAttribute("keywords", keywords);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", keywords.size());
            model.addAttribute("message", message);
            return "keywords";
        }

        List<Keyword> keywords = keywordService.findByName(keywordName);
        model.addAttribute("keywords", keywords);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", keywords.size());
        model.addAttribute("message", "La keyword ha sido creada correctamente.");
        return "redirect:/keywords";
    }

    @PostMapping("/delete-keyword")
    public String deleteKeyword(@RequestParam Integer keywordId) {
        keywordService.deleteKeyword(keywordId);
        return "redirect:/keywords";
    }

    @GetMapping("/update-keyword")
    public String showUpdateForm(@RequestParam Integer keywordId, Model model) {
        Keyword keyword = keywordService.getKeywordById(keywordId);
        model.addAttribute("keyword", keyword);
        return "updatekeyword";
    }

    @PostMapping("/update-keyword")
    public String updateKeyword(@RequestParam Integer keywordId, @RequestParam String keywordName, Model model) {
        String trimmedName = keywordName.trim();
        if (trimmedName.isEmpty() || keywordName.startsWith(" ") || keywordName.endsWith(" ")) {
            model.addAttribute("error", "El nombre no puede estar vacío o tener espacios al principio/final.");
            Keyword keyword = keywordService.getKeywordById(keywordId);
            model.addAttribute("keyword", keyword);
            return "updatekeyword";
        }
        String message = keywordService.updateKeywordNameById(keywordId, keywordName);
        if (message != null) {
            model.addAttribute("error", message);
            Keyword keyword = keywordService.getKeywordById(keywordId);
            model.addAttribute("keyword", keyword);
            return "updatekeyword";
        }
        model.addAttribute("error", "La keyword ha sido modificada con éxito.");
        Keyword keyword = keywordService.getKeywordById(keywordId);
        model.addAttribute("keyword", keyword);
        return "updatekeyword";
    }
}
