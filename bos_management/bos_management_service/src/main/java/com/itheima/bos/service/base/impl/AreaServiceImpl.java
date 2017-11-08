package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 10:22:36 AM <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    
    @Autowired
    private AreaRepository areaRepository;
    
    @Override
    public void save(List<Area> list) {
        areaRepository.save(list);
    }

    @Override
    public Page<Area> pageQuery(PageRequest pageRequest) {
        Page<Area> list = areaRepository.findAll(pageRequest);
        return list;
    }

}
  
