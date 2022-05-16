package com.pet.pay.service.impl;

import com.pet.pay.entity.Product;
import com.pet.pay.mapper.ProductMapper;
import com.pet.pay.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
