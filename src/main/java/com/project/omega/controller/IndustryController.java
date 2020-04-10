package com.project.omega.controller;


import com.project.omega.bean.dao.Industry;
import com.project.omega.exceptions.NoRecordsFoundException;
import com.project.omega.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/industry")
public class IndustryController {

    @Autowired
    IndustryService industryService;

    @PostMapping(value = "/create")
    public ResponseEntity createIndustry(@RequestBody Industry industry) {
        boolean industryCreated = industryService.createIndustry(industry);
        if (industryCreated) {
            return ResponseEntity.ok("Successful created new industry");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Industry Name Already Exists in DB");
    }

    //get all api
    @GetMapping(value = "/get")
    public ResponseEntity getAllIndustries() {
        List<Industry> industries = industryService.getAllIndustry();
        return new ResponseEntity(industries, HttpStatus.OK);
    }
    //get by id api

    @GetMapping(value = "/{id}")
    public ResponseEntity getIndustryById(@PathVariable(value = "id") Long id) {
        Industry industry = industryService.getIndustryById(id);
        return new ResponseEntity(industry, HttpStatus.OK);
    }

    //delete by id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteIndustryById(@PathVariable(value = "id") Long id) {
        boolean foundAndDeleted = industryService.deleteIndustryById(id);
        if (foundAndDeleted) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Industry doesn't exist in Database to be deleted");
    }

    @GetMapping(value = "/getByName/{name}")
    public ResponseEntity getIndustryByName(@PathVariable(value = "name") String name) throws NoRecordsFoundException {
        Industry industry = industryService.getIndustryByName(name);
        return new ResponseEntity(industry, HttpStatus.OK);
    }

    //update by id
    @PutMapping(value = {"/update/{id}"})
    public ResponseEntity updateIndustry(@PathVariable(value = "id") Long id, @RequestBody Industry update) {
        Boolean industry = industryService.updateIndustry(id, update);
        if (industry) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID of Industry to be updated not found in DB");
    }

}




