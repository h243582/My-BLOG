package com.hyf.service;

import com.hyf.dao.BrandDao;
import com.hyf.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    BrandDao brandDao;

    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    public Brand findById(Integer id) {
        return brandDao.findById(id).get();
    }

    public void addBrand(Brand brand) {

        brandDao.save(brand);
    }

    public void updateBrand(Brand brand) {
        brandDao.save(brand);
    }

    public void deleteById(Integer id) {
        brandDao.deleteById(id);
    }



}
