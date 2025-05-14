package com.culturemoa.cultureMoaProject.board.controller;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import com.culturemoa.cultureMoaProject.board.service.ContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("contents")
@CrossOrigin(origins = "*")
public class ContentsController {
    @Autowired
    private ContentsService contentsService;

    @GetMapping("/board")
    public List<ContentsDTO> contents() {
        System.out.println(contentsService.getAllContents());

        return contentsService.getAllContents();
    }

}
