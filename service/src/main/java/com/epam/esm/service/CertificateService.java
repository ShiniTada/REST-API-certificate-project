package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;
import java.util.List;
import java.util.Set;

public interface CertificateService extends MainService<CertificateDTO, Long> {
    List<CertificateDTO> findAll(QuerySpecificationDTO querySpecificationDTO);

    CertificateDTO update(CertificateDTO certificateDTO);

    CertificateDTO applyPatch(Long id, PatchDTO patchDTO);

    CertificateDTO attachTags(CertificateDTO certificateDTO, Set<TagDTO> tagDTOSet);
}
