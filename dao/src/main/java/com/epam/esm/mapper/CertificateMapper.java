package com.epam.esm.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class CertificateMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(resultSet.getLong("id"));
        certificate.setName(resultSet.getString("name"));
        certificate.setDescription(resultSet.getString("description"));
        certificate.setPrice(resultSet.getBigDecimal("price"));
        certificate.setDuration(resultSet.getInt("duration"));
        certificate.setCreateDate(ZonedDateTime.ofInstant(resultSet.getTimestamp("create_date").toInstant(),
                ZoneId.systemDefault()));
        certificate.setLastUpdateDate(ZonedDateTime.ofInstant(resultSet.getTimestamp("last_update_date").toInstant(),
                ZoneId.systemDefault()));

        return certificate;
    }
}
