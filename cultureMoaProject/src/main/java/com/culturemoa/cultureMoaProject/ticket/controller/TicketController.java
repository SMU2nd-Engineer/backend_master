package com.culturemoa.cultureMoaProject.ticket.controller;

import com.culturemoa.cultureMoaProject.ticket.dto.DateGenreCountDTO;
import com.culturemoa.cultureMoaProject.ticket.dto.TicketDTO;
import com.culturemoa.cultureMoaProject.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

//    // 날짜별
//    // 전체 날짜별 + 장르별 개수 목록
//    @GetMapping("/dateGenre")
//    public List<DateGenreCountDTO> getDateGenreCount() {
//        return ticketService.getDateGenreCount();
//    }
//    // 해당 날짜별 전체 데이터
//    @GetMapping("/dateData")
//    public List<TicketDTO> getSelectedDateData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate) {
//        return ticketService.getSelectedDateData(selectedDate);
//    }
//
//    // 다중 조회
//    // 장르 조회 후 날짜 별 티켓 개수 카운트
//    @GetMapping("/selectedGenre")
//    public List<TicketDTO> getSelectedGenreDateCount(@RequestParam String selectedGenre) {
//        return ticketService.getSelectedGenreDateCount(selectedGenre);
//    }
//    // 장르 조회 후 해당 날짜 선택 후 해당 날짜의 데이터 추출





}


