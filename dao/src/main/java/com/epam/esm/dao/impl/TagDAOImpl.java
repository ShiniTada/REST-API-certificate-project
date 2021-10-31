package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TagDAOImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    private static final String SQL_SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id,name FROM tag INNER JOIN relationship_certificates_and_tags " +
            "ON id = tag_id WHERE gift_certificate_id = ?";
    private static final String SQL_INSERT_TAG = "INSERT INTO tag(name) VALUES(?)";
    private static final String SQL_INSERT_RELATIONSHIP_CERTIFICATES_AND_TEG = "INSERT INTO relationship_certificates_and_tags(tag_id,gift_certificate_id) " +
            "VALUES(?,?);";
    private static final String SQL_SELECT_TAG_BY_NAME = "SELECT id,name FROM tag WHERE name = ?";
    private static final String SQL_SELECT_ALL_TAGS = "SELECT id,name FROM tag";
    private static final String SQL_SELECT_TAG_BY_ID = "SELECT id,name FROM tag WHERE id = ?";
    private static final String SQL_DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?";
    private static final String SQL_DELETE_RELATIONSHIP_CERTIFICATES_AND_TEG_BY_ID = "DELETE FROM relationship_certificates_and_tags WHERE tag_id = ?";

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_TAGS, tagMapper);
    }

    @Override
    public Tag create(Tag tag) {
        Optional<Tag> optionalTag = findByName(tag.getName());
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_TAG_BY_ID, new Object[]{id}, new int[]{Types.INTEGER},
                    tagMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        boolean flag = jdbcTemplate.update(SQL_DELETE_TAG_BY_ID, id) > 0;
        if (flag) {
            jdbcTemplate.update(SQL_DELETE_RELATIONSHIP_CERTIFICATES_AND_TEG_BY_ID, id);
        }
        return flag;
    }

    @Override
    public List<Tag> findAllByCertificateId(Long certificateId) {
        return jdbcTemplate.query(SQL_SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{certificateId}, new int[]{Types.INTEGER}, tagMapper);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_TAG_BY_NAME, new Object[]{name}, new int[]{Types.VARCHAR}, tagMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    @Override
    public void attachToCertificateById(Long tagId, Long certificateId) {
        jdbcTemplate.update(SQL_INSERT_RELATIONSHIP_CERTIFICATES_AND_TEG, tagId, certificateId);
    }
}
