package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 2:40:03 PM <br/>       
 */
public interface CourierService {
    
    /**
     * 快递员设置 的保存 操作
     * save:. <br/>  
     *  
     * @param model
     */
    void save(Courier model);
    /**
     * 快递员设置 功能的 带条件分页查询
     * pageQuery:. <br/>  
     * @param specification 
     *  
     * @param pageRequest
     * @return
     */
    Page<Courier> pageQuery(Specification<Courier> specification, PageRequest pageRequest);
    
    /**
     * 快递员设置 功能的 无条件分页查询
     * pageQuery:. <br/>  
     * @param specification 
     *  
     * @param pageRequest
     * @return
     */
    Page<Courier> pageQuery( PageRequest pageRequest);
    
    /**
     * 批量 逻辑删除快递员  即 把 deltag 作废标志 1 为标记作废
     * batchDel:. <br/>  
     *  
     * @param ids
     */
    void batchDel(String ids);
    
}
  
