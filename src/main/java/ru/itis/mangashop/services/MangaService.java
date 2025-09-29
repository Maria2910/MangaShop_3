package ru.itis.mangashop.services;

import ru.itis.mangashop.entities.Manga;
import java.util.List;

public interface MangaService {
    Manga getMangaById(Long id);
    List<Manga> findMangas(String search, Long categoryId);
    Manga saveManga(Manga manga);
}