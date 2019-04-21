package com.springboot.pos.dao;

import com.springboot.pos.reposity.DiningTableTypeReposity;
import org.springframework.data.jpa.repository.Query;

public interface IDiningTableTypeDAO extends DiningTableTypeReposity {
    /**
     * 根据餐台人数查询可用餐台类型
     * @param dtt_num
     * @return
     */
    @Query(value = "select dtt_id from dining_table_type where dtt_num = ?1", nativeQuery = true)
    String findDttByNum(int dtt_num);
}
