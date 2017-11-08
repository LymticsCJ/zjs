package com.itheima.bos.web.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRespositoryTest <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 10:37:24 AM <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRespositoryTest {
    
    @Autowired
    private StandardRepository standardRepository;
    
    @Test
    public void testSave() {
        Standard standard = new Standard();
        standard.setMaxLength(700);
        standardRepository.save(standard);
    }
    @Test
    public void testDelete(){
       
        standardRepository.delete(1L);
    }
    
    @Test
    public void findByAll() {

        List<Standard> findAll = standardRepository.findAll();
        
        for (Standard standard : findAll) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void testUpate(){
        Standard standard = new Standard();
        standard.setId(2L);
        standard.setMaxLength(320);
        standardRepository.save(standard);
    }
    
    @Test
    public void findByName() {
        List<Standard> list = standardRepository.findByName("张三");
        for (Standard standard : list) {
            System.err.println(standard);
        }
    }
    @Test
    public void findByName1() {
        List<Standard> list = standardRepository.findByNameaaa("张三");
        for (Standard standard : list) {
            System.err.println(standard);
        }
    }
    @Test
    public void findByNameAndMaxLength() {
       Standard standard = standardRepository.findByNameAndMaxLength("张三", 320);
         System.out.println(standard);       
    }
    @Test
    public void findByNameAndMaxLengthaaa() {
       Standard standard = standardRepository.findByNameAndMaxLength("张三", 320);
         System.out.println(standard);       
    }
    @Test
    public void findByNameAndMaxLengthaa() {
        Standard standard =standardRepository.findByNameAndMaxLengthaa("张三", 320);
        System.out.println(standard);
    }
    
    @Test
    public void findByNameLike() {
        Standard standard =standardRepository.findByNameLike("%三");
        System.out.println(standard);
    }
    @Test
    public void testUpdate() {
        // 自定义删除
        standardRepository.deleteByName("张三");
    }
    
    @Test
    public void testUpdate2(){
        standardRepository.updateByName(550, "李四");
    }
}
  
