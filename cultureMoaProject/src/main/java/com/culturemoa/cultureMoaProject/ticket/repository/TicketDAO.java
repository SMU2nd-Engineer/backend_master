package com.culturemoa.cultureMoaProject.ticket.repository;

import com.culturemoa.cultureMoaProject.ticket.dto.DateCountDTO;
import com.culturemoa.cultureMoaProject.ticket.dto.TicketDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TicketDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public TicketDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    // 티켓 전체
    public List<TicketDTO> getTickets() {
        return sqlSessionTemplate.selectList("ticketMapper.getAllTicket");
    }

    public TicketDTO getTicketInfo(int idx) {
        Map<String, Object> params = new HashMap<>();
        params.put("idx", idx);
        params.put("today", LocalDate.now().toString());

        return sqlSessionTemplate.selectOne("ticketMapper.getTicketInfo", params);
    }

    public List<TicketDTO> getTicketBySearchQuery(List<Integer> categories, String query, LocalDate startDate, LocalDate endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("categories", categories);  // 리스트 형태로 넘기기
        params.put("query", query);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("today", LocalDate.now().toString());

        return sqlSessionTemplate.selectList("ticketMapper.getTicketBySearchQuery", params);
    }

    public List<DateCountDTO> getDateCountsByMonthAndCategories(Map<String, Object> paramMap) {
        if (!paramMap.containsKey("today")) {
            paramMap.put("today", LocalDate.now().toString());
        }
        // 리스트 형태로 넘기기
        return sqlSessionTemplate.selectList("ticketMapper.getDateCountsByMonthAndCategories", paramMap);
    }
}
