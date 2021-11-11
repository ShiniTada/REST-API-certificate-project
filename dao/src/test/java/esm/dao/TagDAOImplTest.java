package esm.dao;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import configuration.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("dev")
class TagDAOImplTest {

    @Autowired
    private TagDAO tagDAO;

    @Test
    void findAllTest() {
        List<Tag> tags = tagDAO.findAll();
        Assertions.assertEquals(7, tags.size());
    }

    @Test
    void createNewValidTest() {
        Tag tag = Tag.builder()
                .name("TOY")
                .build();
        Tag actual = tagDAO.create(tag);
        Assertions.assertEquals(tag.getName(), actual.getName());
    }

    @Test
    void createExistingTest() {
        Tag tag = Tag.builder()
                .name("SPA")
                .build();
        Tag actual = tagDAO.create(tag);
        Assertions.assertEquals(tag.getName(), actual.getName());
    }

    @Test
    void findByIdTest() {
        Long id = 1L;
        Optional<Tag> actual = tagDAO.findById(id);
        Assertions.assertEquals("SPA", actual.get().getName());
    }

    @Test
    void findByIdNotExistsTest() {
        Long id = 112L;
        Optional<Tag> actual = tagDAO.findById(id);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void deleteTrueTest() {
        Long id = 1L;
        boolean flag = tagDAO.delete(id);
        Assertions.assertTrue(flag);
    }

    @Test
    void deleteFalseTest() {
        Long id = 112L;
        boolean flag = tagDAO.delete(id);
        Assertions.assertFalse(flag);
    }

    @Test
    void findAllByCertificateIdTest() {
        Long id = 1L;
        List<Tag> tags = tagDAO.findAllByCertificateId(id);
        Assertions.assertFalse(tags.isEmpty());
    }

    @Test
    void findAllByCertificateIdEmptyTest() {
        Long id = 5L;
        List<Tag> tags = tagDAO.findAllByCertificateId(id);
        Assertions.assertFalse(tags.isEmpty());
    }

    @Test
    void findByTagNameTest() {
        String name = "SPA";
        Optional<Tag> actual = tagDAO.findByName(name);
        Assertions.assertEquals(name, actual.get().getName());
    }

    @Test
    void findByTagNameNotExistsTest() {
        String name = "for test";
        Optional<Tag> actual = tagDAO.findByName(name);
        Assertions.assertTrue(actual.isEmpty());
    }

}
