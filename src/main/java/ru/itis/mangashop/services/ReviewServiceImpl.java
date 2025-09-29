package ru.itis.mangashop.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.Review;
import ru.itis.mangashop.entities.User;
import ru.itis.mangashop.repositories.ReviewRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public Review createReview(Review review, User user, Manga manga) {
        review.setUser(user);
        review.setManga(manga);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getMangaReviews(Manga manga) {
        return reviewRepository.findByMangaOrderByCreatedAtDesc(manga);
    }

    @Override
    public boolean hasUserReviewed(Manga manga, User user) {
        return reviewRepository.existsByMangaAndUser(manga, user);
    }

    @Override
    public Double getAverageRating(Manga manga) {
        List<Review> reviews = getMangaReviews(manga);
        if (reviews.isEmpty()) {
            return 0.0;
        }

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}