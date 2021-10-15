package com.hyf.service;

import com.hyf.pojo.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    public List<Brand> findAll();

    public Brand findById(Integer id);

    public void addBrand(Brand brand);

    public void updateBrand(Brand brand);

    public void deleteById(Integer id);



}
