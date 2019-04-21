package com.springboot.pos.service.impl;

import com.springboot.pos.dao.ISaleLineItemDAO;
import com.springboot.pos.entity.SaleLineItem;
import com.springboot.pos.reposity.SaleLineItemReposity;
import com.springboot.pos.service.ISaleLineItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SaleLineItemService implements ISaleLineItemService {
    @Resource
    private ISaleLineItemDAO iSaleLineItemDAO;

    @Transactional
    public SaleLineItem saveSlt(SaleLineItem saleLineItem){
        return iSaleLineItemDAO.save(saleLineItem);
    }

    //截取id
    @Transactional
    public String generateId(){
        return iSaleLineItemDAO.generateId();
    }

    @Transactional
    public Iterable<SaleLineItem> displaySli(String sli){
        Iterable<SaleLineItem> saleLineItems = iSaleLineItemDAO.displaySli(sli);
        return saleLineItems;
    }

    @Transactional
    public void deleteSlt(SaleLineItem saleLineItem){
        iSaleLineItemDAO.delete(saleLineItem);
    }

    //查询订单数目
    @Transactional
    public int countSli(String sale_id){
        int sum = iSaleLineItemDAO.countSli(sale_id);
        return sum;
    }
}
