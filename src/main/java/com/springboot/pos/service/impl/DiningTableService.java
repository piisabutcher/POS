package com.springboot.pos.service.impl;

import com.springboot.pos.dao.IDiningTableDAO;
import com.springboot.pos.dao.IDiningTableTypeDAO;
import com.springboot.pos.entity.DiningTable;
import com.springboot.pos.entity.DiningTableType;
import com.springboot.pos.reposity.DiningTableReposity;
import com.springboot.pos.reposity.DiningTableTypeReposity;
import com.springboot.pos.service.IDiningTableService;
import com.springboot.pos.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DiningTableService implements IDiningTableService {

    @Resource
    private IDiningTableDAO iDiningTableDAO;
    @Resource
    private IDiningTableTypeDAO iDiningTableTypeDAO;

    /**
     * 通过餐桌类型人数查找可就餐餐台；
     * @param dtt_num
     * @return
     */
    @Transactional
    public Iterable<DiningTable> findByDtt(int dtt_num){
        String dtt_id = iDiningTableTypeDAO.findDttByNum(dtt_num);
        Iterable<DiningTable> diningTables = iDiningTableDAO.findByDtt(dtt_id);
        return diningTables;
    }

    @Transactional
    public DiningTable findById(String dt_id){
        Optional<DiningTable> diningTable =  iDiningTableDAO.findById(dt_id);
        return diningTable.isPresent()?diningTable.get():null;
    }
}
