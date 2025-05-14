package com.culturemoa.cultureMoaProject.ticket.service;

import com.culturemoa.cultureMoaProject.ticket.dto.TicketDTO;
import com.culturemoa.cultureMoaProject.ticket.repository.TicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketDAO ticketDAO;

    @Autowired
    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }
    // 티켓 전체
    public List<TicketDTO> getAllTicket(){
        return ticketDAO.getAllTicket();
    }

    // 장르별
    // 전체 장르 목록
    public List<String> getAllGenres() {
        return ticketDAO.getAllGenres();
    }
    // 선택된 장르의 티켓 리스트
    public List<TicketDTO> getTicketsByGenre(String genreName) {
        return ticketDAO.getTicketsByGenre(genreName);
    }

}
