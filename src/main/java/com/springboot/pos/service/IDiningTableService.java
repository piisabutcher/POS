package com.springboot.pos.service;

import com.springboot.pos.entity.DiningTable;

public interface IDiningTableService {
    public Iterable<DiningTable> findByDtt(int dtt_num);

    public DiningTable findById(String dt_id);
}
