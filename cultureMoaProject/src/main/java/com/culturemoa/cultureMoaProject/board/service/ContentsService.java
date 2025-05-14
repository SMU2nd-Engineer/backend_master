package com.culturemoa.cultureMoaProject.board.service;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import com.culturemoa.cultureMoaProject.board.repository.ContentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentsService {
    private final ContentsDAO contentsDAO;

    @Autowired
    public ContentsService(ContentsDAO contentsDAO) {
        this.contentsDAO = contentsDAO;
    }

    public List<ContentsDTO> getAllContents() {
        return  contentsDAO.getAllContents();
    }
}
