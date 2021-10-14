package com.hyf.controller;

import com.hyf.pojo.Brand;
import com.hyf.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public List<Brand> query1(){
        return brandService.findAll();
    }

    @GetMapping("{id}")
    public Brand query1(@PathVariable Integer id){
        return brandService.findById(id);
    }

    @PostMapping
    public void addBrand(Brand brand) {
        brandService.addBrand(brand);
    }

    @PutMapping
    public void updateBrand(Brand brand) {
        brandService.updateBrand(brand);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        brandService.deleteById(id);
    }

}
