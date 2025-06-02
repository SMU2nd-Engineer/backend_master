package com.culturemoa.cultureMoaProject.ticket.controller;

import com.culturemoa.cultureMoaProject.ticket.dto.DateCountDTO;
import com.culturemoa.cultureMoaProject.ticket.dto.TicketDTO;
import com.culturemoa.cultureMoaProject.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 보통은 List타입 반환은 getTickets 를 사용하구요
    // 만약 조건이 걸려서 다른 내용이라면 getTicketsBy조건 이런식 네이밍이 맞습니다.
    // 티켓 전체
    @GetMapping("/list")
    public List<TicketDTO> getAllTicket(){
        return ticketService.getAllTicket();
    }
    @GetMapping("/{idx}")
    public TicketDTO getTicketInfo(@PathVariable int idx){ return ticketService.getTicketInfo(idx); }

    // 검색
    @GetMapping("/search")
    public List<TicketDTO> searchTickets(
            @RequestParam(value = "categories", required = false) String categories,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate){
        // categories나 query가 없으면 빈 문자열 또는 null일 수 있음
        return ticketService.searchTickets(categories, query, startDate, endDate);
    }

    // 마찬가지 getDateCounts 가 맞을듯 조건이 있다면 By~
    @GetMapping("/calendar")
    public List<DateCountDTO> getCalendarTicketCount(@RequestParam("month") String month, @RequestParam(value = "categories", required = false) String categories) {
        return ticketService.getCalendarTicketCount(month, categories);
    }


}


