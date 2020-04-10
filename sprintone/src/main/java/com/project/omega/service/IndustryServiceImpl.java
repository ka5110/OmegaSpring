package com.project.omega.service;

import com.project.omega.bean.dao.Industry;
import com.project.omega.exceptions.NoRecordsFoundException;

import com.project.omega.helper.Constant;
import com.project.omega.repository.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    @Autowired
    IndustryRepository industryRepository;

    public boolean createIndustry(Industry industry) {
        if (industryRepository.existsByName(industry.getName())) {
            return false;
        }
        industryRepository.save(industry);
        return true;
    }

    public List<Industry> getAllIndustry() {
        List<Industry> industries = (List) industryRepository.findAll();
        return industries;
    }

    public Industry getIndustryById(Long id) {
        Optional<Industry> industry = industryRepository.findById(id);

        if (!industry.isPresent()) {
            //throw new NoRecordsFoundException(Constant.ERROR_NO_RECORDS);
            throw new RuntimeException(Constant.ERROR_NO_RECORDS);
        }
        return industry.get();
    }

    public boolean deleteIndustryById(Long id) {
        Optional<Industry> industry = industryRepository.findById(id);
        if (!industry.isPresent()) {
            return false;
        }
        industryRepository.deleteById(id);
        return true;
    }

    public Industry getIndustryByName(String name) throws NoRecordsFoundException {
        Optional<Industry> industry = industryRepository.findByName(name);
        if(!industry.isPresent()) {
            throw new NoRecordsFoundException(Constant.ERROR_NO_RECORDS);
        }
        return industry.get();
    }

    public boolean updateIndustry(Long id, Industry newIndustry) {
        Optional<Industry> industry = industryRepository.findById(id);
        if (!industry.isPresent()) {
            return false;
        }

        if (newIndustry.getName() != null && newIndustry.getDescription() != null) {
            Industry i = new Industry.IndustryBuilder()
                    .setId(id)
                    .setDescription(newIndustry.getDescription())
                    .setName(newIndustry.getName())
                    .build();
            industryRepository.save(i);
            return true;
        } else {
            throw new RuntimeException("Null values detected.");
        }
    }
}
