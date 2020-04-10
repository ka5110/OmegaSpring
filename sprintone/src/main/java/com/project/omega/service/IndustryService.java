package com.project.omega.service;

import com.project.omega.bean.dao.Industry;
import com.project.omega.exceptions.NoRecordsFoundException;

import java.util.List;

public interface  IndustryService {

    boolean createIndustry(Industry industry);

    List<Industry> getAllIndustry();

    Industry getIndustryById(Long id);

    boolean deleteIndustryById(Long id);

    boolean updateIndustry(Long id, Industry newIndustry);

    Industry getIndustryByName(String name) throws NoRecordsFoundException;
}
