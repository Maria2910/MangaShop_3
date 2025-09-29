package ru.itis.mangashop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.mangashop.dto.MangaUpdateRequest;
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
    public void saveManga(Manga manga) {
        mangaRepository.save(manga);
    }

    public List<Manga> findAll() {
        return mangaRepository.findAllByOrderByIdAsc();
    }

    public Manga findById(Long id) {
        return mangaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manga not found with id: " + id));
    }

    public Manga updateManga(Long id, MangaUpdateRequest updateRequest) {
        try {
            System.out.println("🔍 Finding manga with id: " + id);
            Manga manga = findById(id);

            System.out.println("📝 Updating manga: " + manga.getTitle());
            System.out.println("Old price: " + manga.getPrice() + ", new: " + updateRequest.getPrice());
            System.out.println("Old stock: " + manga.getStockQuantity() + ", new: " + updateRequest.getStockQuantity());

            if (updateRequest.getPrice() != null) {
                manga.setPrice(updateRequest.getPrice());
            }
            if (updateRequest.getStockQuantity() != null) {
                manga.setStockQuantity(updateRequest.getStockQuantity());
            }

            Manga saved = mangaRepository.save(manga);
            System.out.println("✅ Manga updated successfully");
            return saved;
        } catch (Exception e) {
            System.out.println("❌ Error in updateManga: " + e.getMessage());
            throw e;
        }
    }

    public void deleteManga(Long id) {
        Manga manga = findById(id);
        mangaRepository.delete(manga);
    }
}