package com.culturemoa.cultureMoaProject.ticket.repository;

import com.culturemoa.cultureMoaProject.ticket.dto.TicketDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    // 장르별
    // 전체 장르 목록
    public List<String> getAllGenres() {
        return sqlSessionTemplate.selectList("ticketMapper.getAllGenres");
    }
    // 선택된 장르의 티켓 리스트
    public List<TicketDTO> getTicketsByGenre(String genreName) {
        return sqlSessionTemplate.selectList("ticketMapper.getTicketsByGenre", genreName);
    }

}
