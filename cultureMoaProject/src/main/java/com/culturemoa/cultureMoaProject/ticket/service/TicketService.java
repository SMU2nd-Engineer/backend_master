package com.culturemoa.cultureMoaProject.ticket.service;

import com.culturemoa.cultureMoaProject.ticket.dto.DateGenreCountDTO;
import com.culturemoa.cultureMoaProject.ticket.dto.TicketDTO;
import com.culturemoa.cultureMoaProject.ticket.repository.TicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    // 검색 기능 메서드
    public List<TicketDTO> searchTickets(String categoriesStr, String query) {
        List<Integer> categories = List.of(); // 빈 리스트 초기화

        if (categoriesStr != null && !categoriesStr.isEmpty()) {
            categories = Arrays.stream(categoriesStr.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        return ticketDAO.getSearch(categories, query);
    }

//    // 장르별
//    // 전체 장르 목록
//    public List<String> getAllGenres() {
//        return ticketDAO.getAllGenres();
//    }
//    // 선택된 장르의 티켓 리스트
//    public List<TicketDTO> getTicketsByGenre(String genreName) {
//        return ticketDAO.getTicketsByGenre(genreName);
//    }
//
//    // 날짜별
//    // 전체 날짜별 + 장르별 개수 목록
//    public List<DateGenreCountDTO> getDateGenreCount() {
//        return ticketDAO.getDateGenreCount();
//    }
//    // 해당 날짜별 전체 데이터
//    public List<TicketDTO> getSelectedDateData(LocalDate selectedDate) {
//        return ticketDAO.getSelectedDateData(selectedDate);
//    }
//
//    // 다중 조회
//    // 장르 조회 후 날짜 별 티켓 개수 카운트
//    public List<TicketDTO> getSelectedGenreDateCount(String selectedGenre) {
//        return ticketDAO.getSelectedGenreDateCount(selectedGenre);
//    }
//    // 장르 조회 후 해당 날짜 선택 후 해당 날짜의 데이터 추출


}
