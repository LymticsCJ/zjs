package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRespository <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:55:55 AM <br/>       
 */
public interface StandardRepository extends JpaRepository<Standard, Long>{
    
    List<Standard> findByName(String name);
    
    @Query("from Standard where name = ?")
    List<Standard> findByNameaaa(String name);
    
    Standard findByNameAndMaxLength(String name,Integer maxLength);
    
 // 自定义多条件查询
    @Query("from Standard where name =?2 and maxLength = ?1")
    Standard findByNameAndMaxLengthaaa(Integer maxLength, String name);
    
 // 使用标准SQL查询
    @Query(value="select * from T_STANDARD where C_NAME = ? and C_MAX_LENGTH = ?",
            nativeQuery = true)
    Standard findByNameAndMaxLengthaa(String name, Integer maxLength);
    

    // 模糊查询
    Standard findByNameLike(String name);
    @Modifying // 代表本操作是更新操作
    @Transactional // 事务注解
    @Query("delete from Standard where name = ?")
    void deleteByName(String name);
    
     @Modifying // 代表本操作是更新操作
    @Transactional // 事务注解
    @Query("update Standard set maxLength = ?   where name = ?")
    void updateByName(Integer maxLength,String name);
}
  
