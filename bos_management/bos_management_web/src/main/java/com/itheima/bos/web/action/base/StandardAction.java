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
import com.itheima.bos.web.common.CommonAction;
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
public class StandardAction extends CommonAction<Standard> {

    public StandardAction() {
          
        super(Standard.class);  
        // TODO Auto-generated constructor stub  
        
    }

    @Autowired
    private StandardService  standardService;
        
    //收派标准的 添加
    @Action(value="standardAction_save",results={@Result(name="success",location ="/pages/base/standard.html",
           type="redirect")})
    public String save(){
        standardService.save(getModel());   
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
        page2json(page, null);
        return NONE;
    }
    
    //查询所有,提供给添加快递员页面使用
    @Action("standardAction_findAll")
    public String  findAll () throws IOException{
        List<Standard> list = standardService.findAll();
        list2Json(list, null);
        System.out.println("StandAction的方法:"+list);
       return NONE;
    }
    
}
  
