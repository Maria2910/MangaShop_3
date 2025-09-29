package ru.itis.mangashop.services;

import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.Review;
import ru.itis.mangashop.entities.User;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    Review createReview(Review review, User user, Manga manga);
    List<Review> getMangaReviews(Manga manga);
    boolean hasUserReviewed(Manga manga, User user);
    Double getAverageRating(Manga manga); // Просто общая средняя оценка
}
