package com.webshoppingmall.repository;

import com.webshoppingmall.dto.ItemSearchDto;
import com.webshoppingmall.dto.MainItemDto;
import com.webshoppingmall.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) ;
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto , Pageable pageable);
}
