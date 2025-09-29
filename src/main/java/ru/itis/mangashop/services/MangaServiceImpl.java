package ru.itis.mangashop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.repositories.MangaRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MangaServiceImpl implements MangaService {

    private final MangaRepository mangaRepository;

    @Override
    public List<Manga> findMangas(String search, Long categoryId) {
        return mangaRepository.findMangas(search, categoryId);
    }

    @Override
    public Manga getMangaById(Long id) {
        return mangaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manga not found with id: " + id));
    }

    @Override
    public Manga saveManga(Manga manga) {
        return mangaRepository.save(manga);
    }
}