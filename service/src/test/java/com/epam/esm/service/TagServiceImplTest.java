package com.epam.esm.service;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    public TagDAO tagDAO;

    @Mock
    public MapperDTO mapperDTO;

    private Tag tag;

    @BeforeEach
    public void initEach() {
        tag = Tag.builder().name("SPA").build();
    }

    @Test
    void findAllTest() {
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        List<TagDTO> expected = tags.stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toList());
        Mockito.when(tagDAO.findAll()).thenReturn(tags);
        List<TagDTO> actual = tagService.findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllEmptyTest() {
        List<Tag> tags = new ArrayList<>();
        List<TagDTO> expected = new ArrayList<>();
        Mockito.when(tagDAO.findAll()).thenReturn(tags);
        List<TagDTO> actual = tagService.findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Optional<Tag> optionalTag = Optional.of(tag);
        Long id = 1L;
        TagDTO expected = mapperDTO.convertTagToDTO(tag);
        Mockito.when(tagDAO.findById(id)).thenReturn(optionalTag);
        TagDTO actual = tagService.findById(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test()
    void findByIdExceptionTest() {
        Long id = 1L;
        Mockito.when(tagDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            tagService.findById(id);
        });
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void createTagTest() {
        Mockito.when(tagDAO.create(tag)).thenReturn(tag);
        TagDTO expected = mapperDTO.convertTagToDTO(tag);
        TagDTO actual = tagService.create(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteTagTest() {
        Long id = 1L;
        Mockito.when(tagDAO.findById(id)).thenReturn(Optional.of(tag));
        Mockito.when(tagDAO.delete(id)).thenReturn(true);
        boolean actual = tagService.delete(id);
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteExceptionTest() {
        Long id = 1L;
        Mockito.when(tagDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            tagService.delete(id);
        });
    }

}