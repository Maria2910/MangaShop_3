package ru.itis.mangashop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.mangashop.dto.ReviewRequest;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.Review;
import ru.itis.mangashop.entities.User;
import ru.itis.mangashop.services.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MainPageController {

    private final MangaService mangaService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public MainPageController(MangaService mangaService, CategoryService categoryService, ReviewService reviewService, UserService userService) {
        this.mangaService = mangaService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getMainPage(@RequestParam(required = false) String search,
                           @RequestParam(required = false) Long categoryId,
                           Model model) {

        String normalizedSearch = (search != null && !search.trim().isEmpty()) ? search.trim() : null;

        List<Manga> mangas = mangaService.findMangas(normalizedSearch, categoryId);

        model.addAttribute("mangas", mangas);
        model.addAttribute("searchQuery", search != null ? search : "");
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "main-page";
    }

    @GetMapping("manga/{id}")
    public String getMangaDetails(@PathVariable Long id, Model model, Principal principal) {
        try {
            Manga manga = mangaService.getMangaById(id);
            List<Review> reviews = reviewService.getMangaReviews(manga);
            Double averageRating = reviewService.getAverageRating(manga);

            model.addAttribute("manga", manga);
            model.addAttribute("reviews", reviews);
            model.addAttribute("averageRating", averageRating); // Просто число, а не Map
            model.addAttribute("reviewRequest", new ReviewRequest());

            boolean hasReviewed = false;
            if (principal != null) {
                User user = userService.findByUsername(principal.getName());
                hasReviewed = reviewService.hasUserReviewed(manga, user);
            }
            model.addAttribute("hasReviewed", hasReviewed);

        } catch (Exception e) {
            Manga manga = mangaService.getMangaById(id);
            model.addAttribute("manga", manga);
            model.addAttribute("reviews", List.of());
            model.addAttribute("averageRating", 0.0);
            model.addAttribute("hasReviewed", false);
        }

        return "manga-details";
    }
}