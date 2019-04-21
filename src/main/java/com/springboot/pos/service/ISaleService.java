package com.springboot.pos.service;

import com.springboot.pos.entity.Sale;

public interface ISaleService {

    public Sale saveSale(Sale sale);
    public String generateId();
    public Sale getSaleById(String sale_id);
    public Iterable<Sale> displayAllSale();
}
