package com.culturemoa.cultureMoaProject.user.repository;

import com.culturemoa.cultureMoaProject.user.dto.UserDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

// 예시로 사용할 것 또는 마이바티스 사용할 예정이라 변경 될 수 도 있다.
@Repository
public class UserDAO {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<UserDTO> userDTORowMapper =
            new RowMapper<UserDTO>() {
                @Override
                public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserDTO user = new UserDTO(
                            rs.getString("USERID"),
                            rs.getString("PASSWORD"),
                            rs.getString("NAME"),
                            rs.getTimestamp("REGDATE").toLocalDateTime());
                    user.setUserIdx(rs.getLong("USERIDX"));
                    return user;
                }
            };

    public UserDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
