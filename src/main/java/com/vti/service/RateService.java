package com.vti.service;

import com.vti.entity.Rate;
import com.vti.repository.IRateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RateService implements IRatesevice {
    @Autowired
    private IRateRepository raterepository;

    @Override
    public Page<Rate> getRatesbyproductId(Long productId, Pageable pageable) {
        return raterepository.findByProductId(productId, pageable);
    }
}
