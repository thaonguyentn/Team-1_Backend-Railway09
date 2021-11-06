
package com.vti.service;

import com.vti.entity.Rate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRatesevice {
    public Page<Rate> getRatesbyproductId(Long productId, Pageable pageable);
}