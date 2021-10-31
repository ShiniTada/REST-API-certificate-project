package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.exception.WrongDataFormException;
import com.epam.esm.service.CertificateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;


@RequestMapping(value = "/certificates")
@AllArgsConstructor
@RestController
public class CertificateController {
    private final CertificateService service;

    @GetMapping
    public List<CertificateDTO> findAllCertificates(QuerySpecificationDTO querySpecificationDTO) {
        return service.findAll(querySpecificationDTO);
    }

    @GetMapping(value = "/{id}")
    public CertificateDTO findCertificateById(@PathVariable long id) {
        if (id <= 0) {
            throw new WrongIdException(id);
        }
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDTO createCertificate(@Valid @RequestBody CertificateDTO certificateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataFormException(bindingResult);
        }
        return service.create(certificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable Long id) {
        if (id <= 0) {
            throw new WrongIdException(id);
        }
        service.delete(id);
    }

    @PutMapping(value = "/{id}")
    public CertificateDTO updateCertificate(@PathVariable long id, @Valid @RequestBody CertificateDTO certificateDTO,
                                            BindingResult bindingResult) {
        if (id <= 0) {
            throw new WrongIdException(id);
        }
        if (bindingResult.hasErrors()) {
            throw new WrongDataFormException(bindingResult);
        }
        certificateDTO.setId(id);
        return service.update(certificateDTO);
    }

    @PatchMapping(value = "/{id}")
    public CertificateDTO patchCertificate(@PathVariable long id, @Valid @RequestBody PatchDTO patchDTO, BindingResult bindingResult) {
        if (id <= 0) {
            throw new WrongIdException(id);
        }
        if (bindingResult.hasErrors()) {
            throw new WrongDataFormException(bindingResult);
        }
        return service.applyPatch(id, patchDTO);
    }
}
