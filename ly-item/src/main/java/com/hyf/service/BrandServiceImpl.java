package com.hyf.service;

import com.hyf.dao.BrandMapper;
import com.hyf.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.findById(id).get();
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.save(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.save(brand);
    }

    @Override
    public void deleteById(Integer id) {
        brandMapper.deleteById(id);
    }
}
