package com.hyf.dao;

import com.hyf.pojo.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrandDao extends JpaRepository<Brand,Integer>{


}
