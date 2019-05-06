package com.springboot.pos.service.impl;

import com.springboot.pos.dao.IPaymentDAO;
import com.springboot.pos.dao.ISaleDAO;
import com.springboot.pos.entity.Payment;
import com.springboot.pos.entity.PaymentType;
import com.springboot.pos.entity.Sale;
import com.springboot.pos.reposity.SaleReposity;
import com.springboot.pos.service.ISaleService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SaleService implements ISaleService {

    @Resource
    private ISaleDAO iSaleDAO;
    @Resource
    private IPaymentDAO iPaymentDAO;

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

    /**
     * 显示所有未付款订单
     * @return
     */
    @Transactional
    public Iterable<Sale> displayAllUncompleteSale(){
        Iterable<Sale> sales= iSaleDAO.displayAllUncompleteSale();
        return sales;
    }

    @Transactional
    public int paySale(Payment payment){
        if(payment.getPay_num() < payment.getSale().getTotal_amt()){
            return 0;//表示支付金额不足以支付这个订单
        }
        iPaymentDAO.save(payment);
        payment.getSale().setIs_complete(1);
        return 1;
    }
}
