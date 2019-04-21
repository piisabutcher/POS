package com.springboot.pos.service.impl;

import com.springboot.pos.dao.ISaleDAO;
import com.springboot.pos.entity.Sale;
import com.springboot.pos.reposity.SaleReposity;
import com.springboot.pos.service.ISaleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SaleService implements ISaleService {

    @Resource
    private ISaleDAO iSaleDAO;

    @Transactional
    public Sale saveSale(Sale sale){
        return iSaleDAO.save(sale);
    }

    //截取id
    @Transactional
    public String generateId(){
        return iSaleDAO.generateId();
    }

    @Transactional
    public Sale getSaleById(String sale_id){
        Optional<Sale> sl = iSaleDAO.findById(sale_id);
        return sl.isPresent()?sl.get():null;
    }

    @Transactional
    public Iterable<Sale> displayAllSale(){
        Iterable<Sale> sales= iSaleDAO.displayAllSale();
        return sales;
    }
}
