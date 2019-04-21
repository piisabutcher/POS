package com.springboot.pos.service;

import com.springboot.pos.entity.SaleLineItem;

public interface ISaleLineItemService {
    public SaleLineItem saveSlt(SaleLineItem saleLineItem);
    public String generateId();
    public Iterable<SaleLineItem> displaySli(String sli);
    public void deleteSlt(SaleLineItem saleLineItem);
    public int countSli(String sale_id);
}
