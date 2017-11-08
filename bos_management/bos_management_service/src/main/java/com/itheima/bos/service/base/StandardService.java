package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 8:57:29 PM <br/>       
 */
public interface StandardService {
    /**
     * 配送标准保存 操作    save方法兼具更新的操作
     * save:. <br/>  
     *  
     * @param model
     */
    void save(Standard model);
    /**
     * 分页查询 配送标准 操作
     * pageQuery:. <br/>  
     *  
     * @param pageRequest
     * @return
     */
    Page<Standard> pageQuery(Pageable pageRequest);
    
    /**
     * 查询 快递员设置中的 取派标准
     */
    List<Standard> findAll();
}
  
