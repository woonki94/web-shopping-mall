package com.webshoppingmall.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

@Getter
@Setter
public class MainItemDto {
    private Long id;

    private String itemName;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    @QueryProjection
    public MainItemDto(Long id, String itemName, String itemDetail, String imgUrl, Integer price){
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
