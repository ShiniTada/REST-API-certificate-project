package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.mapper.CertificateMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Repository
@AllArgsConstructor
public class CertificateDAOImpl implements CertificateDAO {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final CertificateMapper certificateMapper;

    private static final String SQL_SELECT_CERTIFICATE_BY_ID = "SELECT id,name,description,price,duration,create_date," +
            "last_update_date FROM gift_certificate WHERE id = ?";
    private static final String SQL_INSERT_CERTIFICATE = "INSERT INTO gift_certificate(name,description,price,duration," +
            "create_date,last_update_date) VALUES(?,?,?,?,?,?)";
    private static final String SQL_DELETE_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SQL_RELATIONSHIP_CERTIFICATES_AND_TEG_BY_ID = "DELETE FROM relationship_certificates_and_tags" +
            " WHERE gift_certificate_id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE gift_certificate SET %s = ? WHERE id = ? ;";
    private static final String SQL_UPDATE_CERTIFICATE = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, " +
            "duration = ?, last_update_date = ? WHERE id = ?";
    private static final String LAST_UPDATE_DATE_COLUMN = "last_update_date";
    private static final String SELECT_CERTIFICATES = "SELECT gift_certificate.id,gift_certificate.name," +
            "gift_certificate.description,gift_certificate.price,gift_certificate.duration,gift_certificate.create_date," +
            "gift_certificate.last_update_date FROM gift_certificate";
    private static final String SELECT_CERTIFICATE_QUERY = "SELECT DISTINCT gift_certificate.id,gift_certificate.name," +
            "gift_certificate.description,gift_certificate.price,gift_certificate.duration,gift_certificate.create_date," +
            "gift_certificate.last_update_date FROM gift_certificate" +
            " LEFT JOIN relationship_certificates_and_tags ON gift_certificate_id = gift_certificate.id " +
            "LEFT JOIN tag ON tag_id = tag.id" +
            " WHERE (tag.name LIKE %s OR gift_certificate.description LIKE %s" +
            " OR gift_certificate.name LIKE %s " +
            "OR gift_certificate.price =:price OR gift_certificate.duration =:duration) ORDER BY %s;";
    private static final String QUERY_SPECIFICATION_ORDER = "order";
    private static final String QUERY_SPECIFICATION_PRICE = "price";
    private static final String QUERY_SPECIFICATION_DURATION = "duration";
    private static final String STRING_GIFT_CERTIFICATE = "gift_certificate.";

    @Override
    public List<Certificate> findAll(QuerySpecification querySpecification) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        String description = "";
        String certificateName = "";
        String tagName = "";

        if (!ObjectUtils.isEmpty(querySpecification.getOrder())) {
            StringBuilder orderQuery = new StringBuilder();
            int size = querySpecification.getOrder().size();

            for (String order : querySpecification.getOrder()) {
                orderQuery.append(STRING_GIFT_CERTIFICATE).append(order);
                size--;
                if (size >= 1) {
                    orderQuery.append(", ");
                }
            }
            parameterSource.addValue(QUERY_SPECIFICATION_ORDER, orderQuery.toString());
        } else {
            parameterSource.addValue(QUERY_SPECIFICATION_ORDER, "gift_certificate.id");
        }

        parameterSource.addValue(QUERY_SPECIFICATION_DURATION, querySpecification.getDuration());
        parameterSource.addValue(QUERY_SPECIFICATION_PRICE, querySpecification.getPrice());
        if (isEmptyFields(querySpecification)) {
            description = "'%" + querySpecification.getDescription() + "%'";
            certificateName = "'%" + querySpecification.getCertificateName() + "%'";
            tagName = "'%" + querySpecification.getTagName() + "%'";
        } else {
            description = "'%%'";
            certificateName = "'%%'";
            tagName = "'%%'";
        }
        String query = String.format(SELECT_CERTIFICATE_QUERY, tagName, description, certificateName, parameterSource.getValue(QUERY_SPECIFICATION_ORDER));

        return namedParameterJdbcTemplate.query(query, parameterSource, certificateMapper);
    }

    private boolean isEmptyFields(QuerySpecification querySpecification) {
        return !ObjectUtils.isEmpty(querySpecification.getCertificateName()) | !ObjectUtils.isEmpty(querySpecification.getTagName())
                | !ObjectUtils.isEmpty(querySpecification.getDescription()) | querySpecification.getPrice() != 0 |
                querySpecification.getDuration() != 0;
    }

    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        certificate.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CERTIFICATE, new String[]{"id"});
            preparedStatement.setString(1, certificate.getName());
            preparedStatement.setString(2, certificate.getDescription());
            preparedStatement.setDouble(3, certificate.getPrice());
            preparedStatement.setInt(4, certificate.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.from(certificate.getCreateDate().toInstant()));
            preparedStatement.setTimestamp(6, Timestamp.from(certificate.getLastUpdateDate().toInstant()));
            return preparedStatement;
        }, keyHolder);
        certificate.setId(keyHolder.getKey().longValue());
        return certificate;
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SELECT_CERTIFICATES, certificateMapper);
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_CERTIFICATE_BY_ID, new Object[]{id}, new int[]{Types.INTEGER},
                    certificateMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Certificate update(Certificate certificate) {
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        jdbcTemplate.update(SQL_UPDATE_CERTIFICATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), Timestamp.from(certificate.getLastUpdateDate().toInstant()), certificate.getId());

        return certificate;
    }

    @Override
    public boolean applyPatch(Map<String, Object> patchValues, Long id) {
        patchValues.forEach((key, value) -> {
            String formattedSQL = String.format(SQL_UPDATE_BY_ID, key);
            jdbcTemplate.update(formattedSQL, value, id);
        });
        String formattedSQL = String.format(SQL_UPDATE_BY_ID, LAST_UPDATE_DATE_COLUMN);
        jdbcTemplate.update(formattedSQL, ZonedDateTime.now(ZoneId.systemDefault()), id);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        boolean flag = jdbcTemplate.update(SQL_DELETE_CERTIFICATE_BY_ID, id) > 0;
        if (flag) {
            jdbcTemplate.update(SQL_RELATIONSHIP_CERTIFICATES_AND_TEG_BY_ID, id);
        }
        return flag;
    }
}
