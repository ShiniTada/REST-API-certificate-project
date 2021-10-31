package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.exception.WrongDataFormException;
import com.epam.esm.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagDTO> findAllTags() {
        return tagService.findAll();
    }

    @GetMapping(value = "/{id}")
    public TagDTO findTagId(@PathVariable Long id) {
        if (id <= 0) {
            throw new WrongIdException(id);
        }
        return tagService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO createTag(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataFormException(bindingResult);
        }
        return tagService.create(tagDTO);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        if (id <= 0) {
            throw new WrongIdException(id);
        }
        tagService.delete(id);
    }
}
