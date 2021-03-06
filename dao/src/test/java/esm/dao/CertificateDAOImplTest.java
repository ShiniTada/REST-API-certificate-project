package esm.dao;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.QuerySpecification;
import configuration.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("dev")
class CertificateDAOImplTest {

    @Autowired
    private CertificateDAO certificateDAO;

    @Test
    void findAllQuerySpecificationTest() {
        QuerySpecification querySpecification = QuerySpecification.builder()
                .tagName("SPA")
                .build();
        List<Certificate> certificates = certificateDAO.findAll(querySpecification);
        Assertions.assertFalse(certificates.isEmpty());
    }

    @Test
    void findAllQuerySpecificationEmptySpecificationTest() {
        QuerySpecification querySpecification = new QuerySpecification();
        List<Certificate> certificates = certificateDAO.findAll(querySpecification);
        Assertions.assertFalse(certificates.isEmpty());
    }

    @Test
    void createTest() {
        Certificate certificate = Certificate.builder()
                .name("name for test create certificate")
                .description("Some description for test")
                .price(100.0)
                .duration(10)
                .build();
        Certificate actual = certificateDAO.create(certificate);
        Assertions.assertEquals(certificate.getName(), actual.getName());
    }

    @Test
    void findAllTest() {
        List<Certificate> certificates = certificateDAO.findAll();
        Assertions.assertFalse(certificates.isEmpty());
    }

    @Test
    void findByIdTest() {
        Long id = 1L;
        Optional<Certificate> actual = certificateDAO.findById(id);
        Assertions.assertFalse(actual.isEmpty());
    }

    @Test
    void findByIdNotExistsTest() {
        Long id = 1244L;
        Optional<Certificate> actual = certificateDAO.findById(id);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void updateTest() {
        Certificate certificate = Certificate.builder()
                .name("name for test create certificate")
                .description("Some description for test")
                .price(100.0)
                .duration(10)
                .build();
        Certificate actual = certificateDAO.update(certificate);
        Assertions.assertEquals(certificate.getName(), actual.getName());
    }

    @Test
    void applyPatchTrueTest() {
        Long id = 1L;
        Map<String, Object> patchValues = new HashMap<>();
        boolean actual = certificateDAO.applyPatch(patchValues, id);
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteTrueTest() {
        Long id = 1L;
        boolean flag = certificateDAO.delete(id);
        Assertions.assertTrue(flag);
    }

    @Test
    void deleteFalseTest() {
        Long id = 1231L;
        boolean flag = certificateDAO.delete(id);
        Assertions.assertFalse(flag);
    }
}
