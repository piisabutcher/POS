package com.springboot.pos.service.impl;

import com.springboot.pos.dao.IDiningTableDAO;
import com.springboot.pos.dao.IPaymentDAO;
import com.springboot.pos.dao.IPaymentTypeDAO;
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
    private IPaymentTypeDAO iPaymentTypeDAO;
    @Resource
    private IPaymentDAO iPaymentDAO;
    @Resource
    private IDiningTableDAO iDiningTableDAO;

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
    public Payment paySale(String pt_id,double pay_num,Sale sale){
        System.out.println("123");
        Payment payment = new Payment();
        if(pay_num < sale.getTotal_amt())
            return null;
        //设置id
        String str = sale.getSaleId();
        str = str + "0";
        payment.setPayment_id(str);

        payment.setSale(sale);
        payment.setPaymentType(iPaymentTypeDAO.findById(pt_id).get());
        payment.setPay_num(pay_num);
        payment.setPay_change(pay_num-sale.getTotal_amt());
        iPaymentDAO.save(payment);
        iSaleDAO.findById(sale.getSaleId()).get().setIs_complete(1);
        iDiningTableDAO.findById(sale.getDiningTable().getDt_Id()).get().setIs_people(0);
        return payment;
    }
}
