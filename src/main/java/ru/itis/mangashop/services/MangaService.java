package ru.itis.mangashop.services;

import ru.itis.mangashop.dto.MangaUpdateRequest;
import ru.itis.mangashop.entities.Manga;
import java.util.List;

public interface MangaService {
    Manga getMangaById(Long id);
    List<Manga> findMangas(String search, Long categoryId);
    void saveManga(Manga manga);
    List<Manga> findAll();
    Manga updateManga(Long id, MangaUpdateRequest updateRequest);
    void deleteManga(Long id);
}