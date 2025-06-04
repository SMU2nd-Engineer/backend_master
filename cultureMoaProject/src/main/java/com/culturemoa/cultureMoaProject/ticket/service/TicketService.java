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
    public List<TicketDTO> getTickets(){
        return ticketDAO.getTickets();
    }

    public TicketDTO getTicketInfo(int idx){
        return ticketDAO.getTicketInfo(idx);
    }

    // 검색 기능 메서드
    public List<TicketDTO> getTicketBySearchQuery(String categoriesStr, String query, LocalDate startDate, LocalDate endDate) {
        List<Integer> categories = List.of(); // 빈 리스트 초기화

        if (categoriesStr != null && !categoriesStr.isEmpty()) {
            categories = Arrays.stream(categoriesStr.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        return ticketDAO.getTicketBySearchQuery(categories, query, startDate, endDate);
    }
    // 공연/스포츠 집계
    public List<DateCountDTO> getDateCountsByMonthAndCategories(String month, String categoriesStr) {
        // 1. month 파라미터에서 시작일과 종료일 추출
        String[] parts = month.split("-");
        int year = Integer.parseInt(parts[0]);
        int mon = Integer.parseInt(parts[1]);
        List<Integer> categories = List.of();

        // 2. 해당 월의 시작일과 종료일 계산
        LocalDate startDate = LocalDate.of(year, mon, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // 3. 파라미터 맵 구성
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", java.sql.Date.valueOf(startDate));
        paramMap.put("endDate", java.sql.Date.valueOf(endDate));

        if (categoriesStr != null && !categoriesStr.isEmpty()) {
            categories = Arrays.stream(categoriesStr.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        paramMap.put("categories", categories);

        // 4. DAO 호출
        return ticketDAO.getDateCountsByMonthAndCategories(paramMap);
    }

}
