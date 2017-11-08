package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 8:44:13 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction2 extends ActionSupport implements ModelDriven<Standard> {

    private Standard model = new Standard();
    
    private int page;
    private int rows;
    
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @Override
    public Standard getModel() {

        return model;
    }
    
    @Autowired
    private StandardService  standardService;
        
    //收派标准的 添加
    @Action(value="standardAction_save",results={@Result(name="success",location ="/pages/base/standard.html",
           type="redirect")})
    public String save(){
        standardService.save(model);   
        return SUCCESS;
    }
    
    //分页查询 收派标准
    @Action(value="standardAction_pageQuery")
    public String pageQuery() throws IOException{
        
        // PageRequest中的page属性是从0开始的
        // EasyUI传递过来的page属性是从1开始的
        // JPA的方法 封装了分页查询的条件 page和rows 决定了下面的page是哪一页的数据集
        Pageable pageRequest = new PageRequest(page-1, rows);
      //结果 得到的page 相当于以前的pageBean
        Page<Standard> page = standardService.pageQuery(pageRequest);
     // 当前页显示的数据 相当于PageBean里的list 和上面的 rows 不一样
        List<Standard> rows = page.getContent();
        // 总数据条数
        long total = page.getTotalElements();
        
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows",  rows);
        // 把对象转化成json数据
        // JSONObject
        // 把list集合对象转化成json数据
        // JSONArray

        // 转化数据为json
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        
        return NONE;
    }
    
    //查询所有,提供给添加快递员页面使用
    @Action("standardAction_findAll")
    public String  findAll () throws IOException{
       List<Standard> list = standardService.findAll();
       String json = JSONArray.fromObject(list).toString();
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("application/json;charset=UTF-8");
       response.getWriter().write(json);
       return NONE;
    }
    
}
  
