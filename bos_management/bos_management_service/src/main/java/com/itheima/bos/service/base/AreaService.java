package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 9:39:05 AM <br/>       
 */

public interface AreaService {

       /**
        * 添加 区域信息
        * save:. <br/>  
        *  
        * @param list
        */
    void save(List<Area> list);
    
    /**
     * 分区 分页查询
     * pageQuery:. <br/>  
     *  
     * @param pageRequest
     * @return
     */
    Page<Area> pageQuery(PageRequest pageRequest);
    
}
  
