package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;


/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 1, 2017 2:42:53 PM <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    
    @Autowired
    private CourierRepository courierRepository;
    
    @Override
    public void save(Courier model) {
        courierRepository.save(model);
    }

    @Override
    public Page<Courier> pageQuery(Specification<Courier> specification,PageRequest pageRequest) {
        Page<Courier> list = courierRepository.findAll(specification,pageRequest);
        return list;
    }
    
    @Override
    public void batchDel(String ids) {
        
        if(!StringUtils.isEmpty(ids)){
            String[] courierIDs = ids.split(",");
            for (String courierID : courierIDs) {
                int parseInt = Integer.parseInt(courierID);
                courierRepository.updateByID((long)parseInt);
            }
        }
    }

    @Override
    public Page<Courier> pageQuery(PageRequest pageRequest) {
        Page<Courier> list = courierRepository.findAll(pageRequest);
        return list;
    }

}
  
