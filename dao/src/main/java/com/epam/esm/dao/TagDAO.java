package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO extends BaseDAO<Tag, Long> {
    List<Tag> findAllByCertificateId(Long certificateId);

    Optional<Tag> findByName(String name);

    void attachToCertificateById(Long tagId, Long certificateId);
}
