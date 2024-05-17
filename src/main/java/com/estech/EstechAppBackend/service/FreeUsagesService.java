package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.FreeUsageConverter;
import com.estech.EstechAppBackend.dto.freeUsages.FreeUsagesDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.FreeUsages;
import com.estech.EstechAppBackend.repository.FreeUsagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeUsagesService {

    @Autowired
    private FreeUsagesRepository freeUsagesRepository;
    @Autowired
    private FreeUsageConverter freeUsageConverter;

    public List<FreeUsagesDTO> getAllFreeUsages() {
        return freeUsageConverter.toFreeUsagesDtos(freeUsagesRepository.findAll());
    }

    public FreeUsagesDTO createFreeUsages(FreeUsagesDTO freeUsagesDTO) {
        FreeUsages freeUsages = freeUsageConverter.toFreeUsages(freeUsagesDTO);

        FreeUsages saved = freeUsagesRepository.save(freeUsages);

        return freeUsageConverter.toFreeUsagesDto(saved);
    }

    public FreeUsagesDTO updateFreeUsage(FreeUsagesDTO freeUsagesDTO) {
        if (freeUsagesDTO.getId() != null) {
            throw new AppException("FreeUsage id must be provided for updating", HttpStatus.BAD_REQUEST);
        }

        FreeUsages freeUsages = freeUsagesRepository.findById(freeUsagesDTO.getId())
                .orElseThrow(() -> new AppException("FreeUsage with id " + freeUsagesDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        freeUsageConverter.updateFreeUsages(freeUsages, freeUsageConverter.toFreeUsages(freeUsagesDTO));

        FreeUsages saved = freeUsagesRepository.save(freeUsages);

        return freeUsageConverter.toFreeUsagesDto(saved);
    }

}
