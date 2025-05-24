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
    public List<TicketDTO> getAllTicket() {
        return sqlSessionTemplate.selectList("ticketMapper.getAllTicket");
    }

    public List<TicketDTO> getSearch(List<Integer> categories, String query, LocalDate startDate, LocalDate endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("categories", categories);  // 리스트 형태로 넘기기
        params.put("query", query);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return sqlSessionTemplate.selectList("ticketMapper.getSearch", params);
    }

    public List<DateCountDTO> getCalendarTicketCount(Map<String, Object> paramMap) {
        // 리스트 형태로 넘기기
        return sqlSessionTemplate.selectList("ticketMapper.getCalendarTicketCount", paramMap);
    }

//    public List<DateCountDTO> getCalendarTicketCount(String month) {
//        return sqlSessionTemplate.selectList("ticketMapper.getCalendarTicketCount", month);
//    }


//    // 장르별
//    // 전체 장르 목록
//    public List<String> getAllGenres() {
//        return sqlSessionTemplate.selectList("ticketMapper.getAllGenres");
//    }
//    // 선택된 장르의 티켓 리스트
//    public List<TicketDTO> getTicketsByGenre(String genreName) {
//        return sqlSessionTemplate.selectList("ticketMapper.getTicketsByGenre", genreName);
//    }
//
//    // 날짜별
//    // 날짜별 + 장르별 개수
//    public List<DateGenreCountDTO> getDateGenreCount() {
//        return sqlSessionTemplate.selectList("ticketMapper.getDateGenre");
//    }
//    // 해당 날짜별 전체 데이터
//    public List<TicketDTO> getSelectedDateData(LocalDate selectedDate) {
//        return sqlSessionTemplate.selectList("ticketMapper.getSelectedDateData", selectedDate);
//    }
//
//    // 다중 조회
//    // 장르 조회 후 날짜 별 티켓 개수 카운트
//    public List<TicketDTO> getSelectedGenreDateCount(String selectedGenre) {
//        return sqlSessionTemplate.selectList("ticketMapper.getSelectedGenreDateCount", selectedGenre);
//    }
//    // 장르 조회 후 해당 날짜 선택 후 해당 날짜의 데이터 추출
//    public List<TicketDTO> getgetSelectedGenreAndDateData(){
//
//    }
}
