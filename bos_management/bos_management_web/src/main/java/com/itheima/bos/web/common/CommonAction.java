package com.itheima.bos.web.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

import com.itheima.bos.domain.base.Area;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/** 抽取 分页查询 重复代码  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 11:29:52 AM <br/>       
 */

public class CommonAction<T> extends ActionSupport implements ModelDriven<T>{
    
    private T model;
    
    private Class<T> clazz;
    
    // 创建一个有参构造 让 子类 获取 model 提供方法
    public CommonAction(Class<T> clazz) {
        this.clazz = clazz;
        
        try {
             model = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    @Override
    public T getModel() {
        return model;
    }
    
    // 这边不能用 private 私有  要 protected 本类和子类
    protected int page;
    protected int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    //抽取 重复代码  分页查询  对象转 json字符串数据
    public void page2json( Page<T> page ,String[] excludes) throws IOException{
        
        long total = page.getTotalElements();
        List<T> rows = page.getContent();
        
        Map<Object, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", rows);
       
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes);
        String json = JSONObject.fromObject(map,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
      
    }
    
    // list数据 转 json字符串数据
    public void list2Json(List<T> list,String[] excludes) throws IOException{
        
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes);
        String json = JSONArray.fromObject(list,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
  
