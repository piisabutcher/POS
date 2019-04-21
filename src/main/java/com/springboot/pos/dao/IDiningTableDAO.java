package com.springboot.pos.dao;

import com.springboot.pos.entity.DiningTable;
import com.springboot.pos.reposity.DiningTableReposity;
import org.springframework.data.jpa.repository.Query;

public interface IDiningTableDAO extends DiningTableReposity {

    @Query(value = "select * from dining_table where dtt_id = ?1 and is_people=0", nativeQuery = true)
    Iterable<DiningTable> findByDtt(String dtt_id);
}
