package com.culturemoa.cultureMoaProject.ticket.service;

import com.culturemoa.cultureMoaProject.ticket.dto.DateCountDTO;
import com.culturemoa.cultureMoaProject.ticket.dto.TicketDTO;
import com.culturemoa.cultureMoaProject.ticket.repository.TicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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
    public List<TicketDTO> searchTickets(String categoriesStr, String query, LocalDate startDate,LocalDate endDate) {
        List<Integer> categories = List.of(); // 빈 리스트 초기화

        if (categoriesStr != null && !categoriesStr.isEmpty()) {
            categories = Arrays.stream(categoriesStr.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        return ticketDAO.getSearch(categories, query, startDate, endDate);
    }
    // 공연/스포츠 집계
    public List<DateCountDTO> getCalendarTicketCount(String month) {
        // 1. month 파라미터에서 시작일과 종료일 추출
        String[] parts = month.split("-");
        int year = Integer.parseInt(parts[0]);
        int mon = Integer.parseInt(parts[1]);

        // 2. 해당 월의 시작일과 종료일 계산
        LocalDate startDate = LocalDate.of(year, mon, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // 3. 파라미터 맵 구성
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", java.sql.Date.valueOf(startDate));
        paramMap.put("endDate", java.sql.Date.valueOf(endDate));

        // 4. DAO 호출
        return ticketDAO.getCalendarTicketCount(paramMap);
    }

//    // 공연/스포츠 집계
//    public  List<DateCountDTO> getCalendarTicketCount(String month) {
//        return ticketDAO.getCalendarTicketCount(month);
//    }

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
