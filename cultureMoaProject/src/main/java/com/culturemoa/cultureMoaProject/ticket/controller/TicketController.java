package com.culturemoa.cultureMoaProject.ticket.controller;

import com.culturemoa.cultureMoaProject.ticket.dto.TicketDTO;
import com.culturemoa.cultureMoaProject.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    // 티켓 전체
    @GetMapping
    public List<TicketDTO> getAllTicket(){
        return ticketService.getAllTicket();
    }

    // 장르별
    // 전체 장르 목록 반환
    @GetMapping("/genre")
    public List<String> getAllGenres() {
        return ticketService.getAllGenres();
    }
    // 선택된 장르의 티켓 리스트 반환
    @GetMapping("/genre/{genreName}")
    public List<TicketDTO> getTicketsByGenre(@PathVariable String genreName) {
        return ticketService.getTicketsByGenre(genreName);
    }




}


