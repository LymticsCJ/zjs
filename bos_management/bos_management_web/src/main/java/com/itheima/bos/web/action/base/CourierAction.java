package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.google.common.base.Predicates;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.common.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: Nov 1, 2017 2:28:32 PM <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends CommonAction<Courier> {
    
    public  CourierAction() {

        super(Courier.class);
    }
   
    // 快递员设置的 添加
    @Autowired
    private CourierService courierService;

    @Action(value = "courierAction_save", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String save() {
        courierService.save(getModel());
        return SUCCESS;
    }

    // 快递员设置的分页查询
    @Action("courierAction_pageQuery")
    public String pageQuery() throws IOException {

        final String courierNum = getModel().getCourierNum();
        final String company = getModel().getCompany();
        final String type = getModel().getType();
        final Standard standard = getModel().getStandard();
        
        //jpa方式的  条件查询 
        //创建条件查询对象
        Specification<Courier> specification = new Specification<Courier>() {

            /**
             * 用于构建where语句
             * 
             * @param root : 根对象通常指泛型类型 这边是courier实体类
             * @param query : 查询对象
             * @param cb : 构造查询条件的对象
             * @return 组合查询条件
             */
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(courierNum)) {
                    Predicate q1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(q1);
                    System.out.println("滴滴滴courieracton");
                }
                if (StringUtils.isNotEmpty(company)) {
                    Predicate q2 =
                            cb.equal(root.get("company").as(String.class), "%" + company + "%");
                    list.add(q2);
                }
                if (standard != null && StringUtils.isNotEmpty(standard.getName())) {
                    Join<Object, Object> join = root.join("standard");
                    Predicate q3 = cb.like(join.get("name").as(String.class), standard.getName());
                    list.add(q3);
                }
                if (StringUtils.isNotEmpty(type)) {
                    Predicate q4 = cb.equal(root.get("type").as(String.class), type);
                    list.add(q4);
                }
                if (list.size() == 0) {
                    return null;
                }
               
               Predicate[] arr = new Predicate[list.size()];
               list.toArray(arr);
               
               return cb.and(arr);
            }
        };

        
        
        PageRequest pageRequest = new PageRequest(page - 1, rows);
        Page<Courier> page = courierService.pageQuery(specification,pageRequest);

        page2json(page, new String[] {"fixedAreas"});

        // 方法1 : 在出现懒加载异常的字段上添加一个属性,fetch=FetchType.EAGER,立刻加载的意思
        // 在实际开发中,为了提供响应速度,返回给前端的页面不应该返回一些前端不需要的数据
        // 方法2 : 在返回数据的时候,不要加载会出现懒加载异常的字段
        // 方法3 : 在字段上添加一个关键字 transient,对应字段的值在执行序列化操作的时候,本字段的值不会执行序列化操作
      
       
        
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    // 逻辑删除 快递员
    @Action(value = "courierAction_batchDel", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String batchDel() {
        courierService.batchDel(ids);
        return SUCCESS;
    }

}
