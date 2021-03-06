package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.util.MapperDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    private CertificateDAO certificateDAO;

    @Mock
    private TagDAO tagDAO;

    @Mock
    private MapperDTO mapperDTO;

    @Mock
    private ObjectMapper objectMapper;

    private static Certificate certificate;
    private static CertificateDTO certificateDTO;

    @BeforeEach
    public void initEach() {
        certificate = Certificate.builder()
                .name("for test")
                .description("some info")
                .price(10.0)
                .duration(60)
                .tags(new HashSet<>())
                .build();
        certificateDTO =  CertificateDTO.builder()
                .name("for test")
                .description("some info")
                .price(10.0)
                .duration(60)
                .tags(new HashSet<>())
                .build();
    }

    @Test
    void createCertificateTest() {
        Mockito.when(certificateDAO.create(certificate)).thenReturn(certificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        Mockito.when(mapperDTO.convertDTOToCertificate(certificateDTO)).thenReturn(certificate);
        CertificateDTO actual = certificateService.create(certificateDTO);
        Assertions.assertEquals(certificateDTO, actual);
    }

    @Test
    void findAllTest() {
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateDTO> expected = new ArrayList<>();
        certificates.add(certificate);
        expected.add(certificateDTO);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateDAO.findAll()).thenReturn(certificates);
        List<CertificateDTO> actual = certificateService.findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllEmptyTest() {
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateDTO> expected = new ArrayList<>();
        Mockito.when(certificateDAO.findAll()).thenReturn(certificates);
        List<CertificateDTO> actual = certificateService.findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Optional<Certificate> optionalCertificate = Optional.of(certificate);
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(optionalCertificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.findById(id);
        Assertions.assertEquals(certificateDTO, actual);
    }

    @Test
    void findByIdExceptionTest() {
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.findById(id);
        });
    }

    @Test
    void findAllQuerySpecificationEmpty() {
        QuerySpecificationDTO querySpecificationDTO = new QuerySpecificationDTO();
        QuerySpecification querySpecification = new QuerySpecification();
        Mockito.when(mapperDTO.convertDTOToQuery(querySpecificationDTO)).thenReturn(querySpecification);
        List<Certificate> certificates = new ArrayList<>();
        Mockito.when(certificateDAO.findAll(querySpecification)).thenReturn(certificates);
        List<CertificateDTO> expected = new ArrayList<>();
        List<CertificateDTO> actual = certificateService.findAll(querySpecificationDTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        Optional<Certificate> optionalCertificate = Optional.of(certificate);
        Long id = 1L;
        certificateDTO.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(optionalCertificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.update(certificateDTO);
        Assertions.assertEquals(certificateDTO, actual);
    }

    @Test
    void updateExceptionTest() {
        Long id = 1L;
        certificateDTO.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.update(certificateDTO);
        });
    }

    @Test
    void applyPatchTest() {
        PatchDTO patchDTO = new PatchDTO();
        Map<String, Object> patchMap = new HashMap<>();
        Mockito.when(objectMapper.convertValue(patchDTO, Map.class)).thenReturn(patchMap);
        Optional<Certificate> optionalCertificate = Optional.of(certificate);
        Long id = 1L;
        certificateDTO.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(optionalCertificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.applyPatch(id,patchDTO);
        Assertions.assertEquals(certificateDTO, actual);
    }

    @Test
    void applyPatchExceptionTest() {
        PatchDTO patchDTO = new PatchDTO();
        Map<String, Object> patchMap = new HashMap<>();
        Mockito.when(objectMapper.convertValue(patchDTO, Map.class)).thenReturn(patchMap);
        Long id = 1L;
        certificateDTO.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.applyPatch(id,patchDTO);
        });
    }

    @Test
    void attachTagsTest() {
        CertificateDTO expected = new CertificateDTO();
        HashSet<TagDTO> tagDTOs = new HashSet<>();
        CertificateDTO actual = certificateService.attachTags(expected, tagDTOs);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteCertificatesTest() {
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.of(new Certificate()));
        Mockito.when(certificateDAO.delete(id)).thenReturn(true);
        boolean flag = certificateService.delete(id);
        Assertions.assertTrue(flag);
    }

    @Test
    void deleteException() {
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.delete(id);
        });
    }
}