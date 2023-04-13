package com.javatemplate.domain.book;

import com.javatemplate.integration.ItBookDetailDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItBookMapper {

    public static Book toBook(ItBookDetailDTO itBookDetailDTO) {
        return Book.builder()
                .name(itBookDetailDTO.getName())
                .subtitle(itBookDetailDTO.getSubtitle())
                .author(itBookDetailDTO.getAuthors())
                .publisher(itBookDetailDTO.getPublisher())
                .isbn13(itBookDetailDTO.getIsbn13())
                .year(itBookDetailDTO.getYear())
                .rating(itBookDetailDTO.getRating())
                .description(itBookDetailDTO.getDesc())
                .price(itBookDetailDTO.getPrice())
                .image(itBookDetailDTO.getImage())
                .build();
    }
}
