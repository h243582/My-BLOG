package com.hyf.controller;

import com.hyf.pojo.Brand;
import com.hyf.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;


    @GetMapping
    public List<Brand> query1(){
        return brandService.findAll();
    }

    @GetMapping("/{id}")
    public Brand query1(@PathVariable Integer id){
        return brandService.findById(id);
    }

    @PostMapping
    public String addBrand(@RequestBody Brand brand) {

        brandService.addBrand(brand);

        return "添加了";
    }

    @PutMapping
    public String updateBrand(@RequestBody Brand brand) {
        brandService.updateBrand(brand);
        return "修改了";
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        brandService.deleteById(id);
    }

}

