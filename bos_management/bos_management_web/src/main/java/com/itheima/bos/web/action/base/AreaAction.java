package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.web.common.CommonAction;
import com.itheima.utils.PinYin4JUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: Nov 3, 2017 9:30:32 AM <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area>{

    public AreaAction() {
        super(Area.class);  
    }



    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Autowired
    private AreaService areaService;

    // 保存 区域数据
    @Action(value = "areaAction_importXLS", results = {
            @Result(name = "success", location = "/pages/base/area.html", type = "redirect")})
    public String importXLS() throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = workbook.getSheetAt(0);

        List<Area> list = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0){
                continue;
            }
            
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
            
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            
            String citycode = PinYin4JUtils.hanziToPinyin(city,"").toUpperCase();
            String[] headCode = PinYin4JUtils.getHeadByString(province+city+district,true);
            
            String shortcode = StringUtils.join(headCode);
            
            Area  area = new Area(province, city, district, postcode, citycode, shortcode);
            
            list.add(area);
        }
        
        areaService.save(list);
        workbook.close();
        
        return SUCCESS;

    }
    
    //调用 page2json 区域分页查询
    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException{
        
        PageRequest pageRequest = new PageRequest(page-1, rows);
        Page<Area> page = areaService.pageQuery(pageRequest);
        
        page2json(page, new String[] {"subareas"});
        return NONE;
        
    }
}
