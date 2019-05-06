package com.springboot.pos.controller;

import com.springboot.pos.entity.*;
import com.springboot.pos.service.impl.DiningTableService;
import com.springboot.pos.service.impl.SaleLineItemService;
import com.springboot.pos.service.impl.SaleService;
import com.springboot.pos.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sale")
public class SaleController {

    @Resource
    private SaleService saleService;
    @Resource
    private DiningTableService diningTableService;
    @Resource
    private SaleLineItemService saleLineItemService;
    private Sale currentSale;

    /**
     *通过餐台类型人数得到空闲的餐台
     * @param dtt_num
     * @return
     */
    @RequestMapping("/getDiningTableByNum")
    @ResponseBody
    private Result<DiningTable> getDiningTableByNum(@RequestParam("dtt_num") int dtt_num){
        Iterable<DiningTable> diningTables = diningTableService.findByDtt(dtt_num);
        return Result.success(diningTables);
    }

    /**
     * 改变订单餐台
     * @param diningTable
     * @return
     */
    @RequestMapping("/exchangeDiningTable")
    @ResponseBody
    public Result<DiningTable> exchangeDiningTable(@RequestBody DiningTable diningTable){
        //把原有餐桌设为无人状态
        currentSale.getDiningTable().setIs_people(0);
        //把现有餐桌设为有人状态
        diningTable.setIs_people(1);
        currentSale.setDiningTable(diningTable);
        return Result.success(diningTable);
    }

    /**
     * 增加就餐人数
     * @param num
     * @return
     */
    @RequestMapping("/addDinner_num")
    @ResponseBody
    public Result<Sale> addDinner_num(@RequestParam("num") int num){
        int dt_num = currentSale.getDiningTable().getDiningTableType().getDtt_num();
        if(dt_num - currentSale.getDinners_num() < num){
            return Result.error(503);
        }
        currentSale.setDinners_num(currentSale.getDinners_num() + num);
        return Result.success(currentSale);
    }

    /**
     * 添加新的销售订单
     * @param sale
     * @param saleLineItemList
     * @return
     */
    @RequestMapping("/makeNewSale")
    @ResponseBody
    private Result<Sale> makeNewSale(@RequestBody Sale sale, @RequestBody List<SaleLineItem> saleLineItemList){
        currentSale = saleService.getSaleById(sale.getSaleId());
        DiningTable diningTable = currentSale.getDiningTable();
        //把餐台设为有人状态
        diningTable.setIs_people(1);
        saleService.saveSale(currentSale);
        //订单号
        String sale_id = currentSale.getSaleId();

        //录入菜品
        int i = 1;
        for (SaleLineItem sli:saleLineItemList) {
            //设置item的id
            String sli_id = sale_id + i;
            sli.setSli_id(sli_id);
            saleLineItemService.saveSlt(sli);
            i++;
            currentSale.setTotal_amt(currentSale.getTotal_amt() + sli.getFood().getPrice() * sli.getSli_num());
        }
        return Result.success(currentSale);
    }

    /**
     * 切换订单sale
     * @param sale
     * @return
     */
    @RequestMapping("/exchangeSale")
    @ResponseBody
    public Result<Sale> exchangeSale(@RequestBody Sale sale){
        currentSale = saleService.getSaleById(sale.getSaleId());
        return Result.success(currentSale);
    }

    /**
     * 显示订单明细
     * @return
     */
    @RequestMapping("/displaySli")
    @ResponseBody
    public Result<SaleLineItem> displaySli(){
        Iterable<SaleLineItem> saleLineItems = saleLineItemService.displaySli(currentSale.getSaleId());
        return Result.success(saleLineItems);
    }


    /**
     * 增加订单Item,先判断菜品是否已在菜单上，口味是否相同
     * @param saleLineItems
     */
    @RequestMapping("/addSli")
    @ResponseBody
    public void addSli(@RequestBody List<SaleLineItem> saleLineItems){
        Iterable<SaleLineItem> slis = saleLineItemService.displaySli(currentSale.getSaleId());
        int i = 0;//判断订单里是否已经有这个菜品
        for (SaleLineItem sli:saleLineItems) {
            for(SaleLineItem sli2:slis){
                //如果菜品与口味一样，则修改item的数量
                if(sli.getFood().getFoodId().equals(sli2.getFood().getFoodId()) && sli.getSli_spicy()==sli2.getSli_spicy()){
                    sli2.setSli_num(sli2.getSli_num() + sli.getSli_num());
                    //修改为未出菜状态
                    sli2.setIs_export(0);
                    currentSale.setTotal_amt(currentSale.getTotal_amt() + sli.getFood().getPrice() * sli.getSli_num());
                    i = 1;
                }
            }
            if(i == 0){
                int x = saleLineItemService.countSli(currentSale.getSaleId()) + 1;
                sli.setSli_id(currentSale.getSaleId() + x);
                saleLineItemService.saveSlt(sli);
                currentSale.setTotal_amt(currentSale.getTotal_amt() + sli.getFood().getPrice() * sli.getSli_num());
            }
            i = 0;
        }
    }

    /**
     * 删除已点菜品，若未出菜，可删除
     * @param saleLineItem
     * @return
     */
    @RequestMapping("/deleteSli")
    @ResponseBody
    public Result<SaleLineItem> deleteSli(@RequestBody SaleLineItem saleLineItem){
        if(saleLineItem.getIs_export()==1){
            return Result.error(502);
        }
        saleLineItemService.deleteSlt(saleLineItem);
        currentSale.setTotal_amt(currentSale.getTotal_amt() - saleLineItem.getFood().getPrice() * saleLineItem.getSli_num());
        return Result.success(saleLineItem);
    }



    /**
     * 显示所有订单
     * @return
     */
    @RequestMapping("/displayAllSale ")
    @ResponseBody
    public Result<Sale> displayAllSale(){
        Iterable<Sale> sales = saleService.displayAllSale();
        return Result.success(sales);
    }

    /**
     * 显示所有未付款订单
     * @return
     */
    @RequestMapping("/displayAllUncompleteSale ")
    @ResponseBody
    public Result<Sale> displayAllUncompleteSale(){
        Iterable<Sale> sales = saleService.displayAllUncompleteSale();
        return Result.success(sales);
    }

    /**
     * 支付订单
     * @param payment
     * @return
     */
    @RequestMapping("/paySale ")
    @ResponseBody
    public Result<Sale> paySale(Payment payment){
        int i = saleService.paySale(payment);
        if(i == 0)
            return Result.error(504);
        else
            return Result.success(payment.getSale());
    }

}
