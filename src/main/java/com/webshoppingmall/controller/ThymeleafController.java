package com.webshoppingmall.controller;

import com.webshoppingmall.dto.ItemDto;
import com.webshoppingmall.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

    @GetMapping(value = "/ex01")
    public String thymeleafEx01(Model model){
        model.addAttribute("data", "타임리프 예제");
        return "thymeleafEX/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemName("테스트상품");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEX/thymeleafEx02";
    }
}
