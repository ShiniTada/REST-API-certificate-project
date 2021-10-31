package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.QuerySpecification;

import java.util.List;
import java.util.Map;

public interface CertificateDAO extends BaseDAO<Certificate, Long> {
    List<Certificate> findAll(QuerySpecification querySpecification);

    Certificate update(Certificate certificate);

    boolean applyPatch(Map<String, Object> patchValues, Long id);
}
