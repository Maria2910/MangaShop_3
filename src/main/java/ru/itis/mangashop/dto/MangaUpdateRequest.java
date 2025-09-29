package ru.itis.mangashop.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MangaUpdateRequest {
    private BigDecimal price;
    private Integer stockQuantity;

    public MangaUpdateRequest() {}

    public MangaUpdateRequest(BigDecimal price, Integer stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
