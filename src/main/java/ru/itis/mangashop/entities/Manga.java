package ru.itis.mangashop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"categories", "reviews"})
@Table(name = "mangas")
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true, length = 100)
    private String title;

    @Column(name = "description", nullable = false, unique = true)
    private String description;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "artist", nullable = false, length = 50)
    private String artist;

    @Column(name = "release_date", length = 20)
    private LocalDate releaseDate;

    @Column(name = "price", nullable = false, length = 20)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false, length = 20)
    private Integer stockQuantity;

    @ManyToMany
    @JoinTable(
            name = "manga_category",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews;

    @Column(name = "image_url", length = 100)
    private String imageUrl;
}
