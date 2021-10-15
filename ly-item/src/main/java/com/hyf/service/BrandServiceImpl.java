package com.hyf.service;

import com.hyf.dao.BrandDao;
import com.hyf.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandDao.findById(id).get();
    }

    @Override
    public void addBrand(Brand brand) {
        brandDao.save(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandDao.save(brand);
    }

    @Override
    public void deleteById(Integer id) {
        brandDao.deleteById(id);
    }
}
