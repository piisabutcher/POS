package com.springboot.pos.service.impl;

import com.springboot.pos.dao.IDiningTableTypeDAO;
import com.springboot.pos.entity.DiningTable;
import com.springboot.pos.entity.DiningTableType;
import com.springboot.pos.reposity.DiningTableTypeReposity;
import com.springboot.pos.service.IDiningTableTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DiningTableTypeService implements IDiningTableTypeService {

    @Resource
    private IDiningTableTypeDAO iDiningTableTypeDAO;

    /**
     * 通过就餐人数查询可用的餐桌类型
     * @param dtt_num
     * @return
     */
    @Transactional
    public String findDttByNum(int dtt_num){
        String dtt_id = iDiningTableTypeDAO.findDttByNum(dtt_num);
        return dtt_id;
    }

}
