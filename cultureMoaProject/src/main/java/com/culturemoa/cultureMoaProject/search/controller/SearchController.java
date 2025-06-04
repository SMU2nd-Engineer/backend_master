package com.culturemoa.cultureMoaProject.search.controller;

import com.culturemoa.cultureMoaProject.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/total")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public Map<String, Object> getTotalSearchByKeyword(@RequestParam("keyword") String keyword){
        return searchService.getTotalSearchByKeyword(keyword);
    }
}
