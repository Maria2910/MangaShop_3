package ru.itis.mangashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.mangashop.dto.ReviewRequest;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.Review;
import ru.itis.mangashop.entities.User;
import ru.itis.mangashop.services.MangaService;
import ru.itis.mangashop.services.ReviewService;
import ru.itis.mangashop.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/manga")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final MangaService mangaService;
    private final UserService userService;

    @PostMapping("/{mangaId}/reviews")
    public String addReview(@PathVariable Long mangaId,
                            @ModelAttribute ReviewRequest reviewRequest,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(principal.getName());
            Manga manga = mangaService.getMangaById(mangaId);

            if (reviewService.hasUserReviewed(manga, user)) {
                redirectAttributes.addFlashAttribute("error", "Вы уже оставляли отзыв на эту мангу");
                return "redirect:/manga/" + mangaId;
            }

            Review review = Review.builder()
                    .comment(reviewRequest.getComment())
                    .rating(reviewRequest.getRating())
                    .build();

            reviewService.createReview(review, user, manga);
            redirectAttributes.addFlashAttribute("success", "Отзыв успешно добавлен!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении отзыва");
        }

        return "redirect:/manga/" + mangaId;
    }
}
