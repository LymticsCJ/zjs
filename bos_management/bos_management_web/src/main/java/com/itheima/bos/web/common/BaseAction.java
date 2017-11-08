package com.itheima.bos.web.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:baseAction <br/>
 * Function: <br/>
 * Date: Nov 3, 2017 7:27:11 PM <br/>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

     public BaseAction() {
        // 获取子类的字节码,获取到了AreaAction的字节码
        Class<? extends BaseAction> childClass = this.getClass();
        
        // 获取父类的类型,获取到了BaseAction的 得不到泛型
        // childClass.getSuperclass();
        // 获取父类的类型,获取到了BaseAction<Area>
        Type genericSuperclass = childClass.getGenericSuperclass();
        
        // 进行类型转换 转成子接口 为了获取下面的 type[] 从而获取泛型
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        
        // 获取类型上声明的泛型组成的数组
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        
        // 获取数组中的第一个泛型
        Class realClass = (Class) actualTypeArguments[0];

         try {
            model = (T) realClass.newInstance();
        } catch (Exception e) {
              
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            
        }
    }
    
    private T model;
    
    @Override
    public T getModel() {

        return model;
    }
    
    protected int page;
    protected int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public void page2Json(Page<T> page ,String[] excludes) throws IOException{
        
        long total = page.getTotalElements();
        List<T> rows = page.getContent();
        
        Map<Object, Object> map = new HashMap<>();
        
        map.put("total",total);
        map.put("rows",rows);
        
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes);
        String json = JSONObject.fromObject(map,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        
    }
    
    public void list2Json(List<T> list,String[] excludes) throws IOException{
        
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes);
        String json = JSONObject.fromObject(list,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    
}
