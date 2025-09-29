package ru.itis.mangashop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.mangashop.dto.MangaUpdateRequest;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.services.MangaService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final MangaService mangaService;

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        List<Manga> mangas = mangaService.findAll();

        int totalMangas = mangas.size();
        int inStock = (int) mangas.stream().filter(m -> m.getStockQuantity() > 0).count();
        int outOfStock = totalMangas - inStock;

        model.addAttribute("mangas", mangas);
        model.addAttribute("totalMangas", totalMangas);
        model.addAttribute("inStock", inStock);
        model.addAttribute("outOfStock", outOfStock);
        model.addAttribute("pageTitle", "Админ-панель");

        return "admin";
    }

    @PostMapping("/admin/mangas/{id}/update-price")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updatePrice(
            @PathVariable Long id,
            @RequestBody Map<String, BigDecimal> request) {

        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("💰 UPDATE PRICE REQUEST for manga " + id + ": " + request.get("price"));

            MangaUpdateRequest updateRequest = new MangaUpdateRequest();
            updateRequest.setPrice(request.get("price"));

            Manga updatedManga = mangaService.updateManga(id, updateRequest);

            response.put("success", true);
            response.put("message", "Цена успешно обновлена");
            response.put("manga", Map.of(
                    "id", updatedManga.getId(),
                    "price", updatedManga.getPrice(),
                    "stockQuantity", updatedManga.getStockQuantity()
            ));

            System.out.println("✅ PRICE UPDATE SUCCESS: " + response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("❌ PRICE UPDATE ERROR: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Ошибка обновления цены: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/admin/mangas/{id}/update-stock")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateStock(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> request) {

        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("📦 UPDATE STOCK REQUEST for manga " + id + ": " + request.get("stockQuantity"));

            MangaUpdateRequest updateRequest = new MangaUpdateRequest();
            updateRequest.setStockQuantity(request.get("stockQuantity"));

            Manga updatedManga = mangaService.updateManga(id, updateRequest);

            response.put("success", true);
            response.put("message", "Количество успешно обновлено");
            response.put("manga", Map.of(
                    "id", updatedManga.getId(),
                    "price", updatedManga.getPrice(),
                    "stockQuantity", updatedManga.getStockQuantity()
            ));

            System.out.println("✅ STOCK UPDATE SUCCESS: " + response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("❌ STOCK UPDATE ERROR: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Ошибка обновления количества: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/admin/mangas/{id}/delete")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteMangaAjax(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            mangaService.deleteManga(id);
            response.put("success", true);
            response.put("message", "Товар успешно удален");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка удаления: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}