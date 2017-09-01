package com.mrli.passwordmanager.action;

import com.mrli.passwordmanager.action.base.BaseAction;
import com.mrli.passwordmanager.domain.PasswordManager;
import com.mrli.passwordmanager.domain.User;
import com.mrli.passwordmanager.service.APMService;
import com.mrli.passwordmanager.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */
@Controller
@Scope("prototype")
public class APMAction extends BaseAction<PasswordManager> {

    @Autowired
    private APMService apmService;

    public String add() throws IOException {
        String flag = "0";
        try {
            User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
            PasswordManager pm = this.getModel();
            pm.setUser(loginUser);
            apmService.add(pm);
        } catch (Exception e) {
            flag = "1";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }

    public String pageQuery() throws IOException {

        User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        apmService.pageQuery(pageBean, loginUser.getId());
        this.writePageBean2Json(pageBean, new String[]{"user"});

        return NONE;
    }


    private String encryptKey;

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String setKey(){
        ServletActionContext.getRequest().getSession().setAttribute("encryptKey",encryptKey);
        return NONE;
    }

    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    public String importXls() throws IOException {

        User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        String encryptKey = (String) ServletActionContext.getRequest().getSession().getAttribute("encryptKey");

        System.out.println(encryptKey);

        if (StringUtils.isBlank(encryptKey)){
            String flag = "0";
            ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
            ServletActionContext.getResponse().getWriter().print(flag);
            return NONE;
        }

        EncryptUtils encryptUtils = new EncryptUtils();
        encryptUtils.setKEY(encryptKey);

        String flag = "1";
        try {
            // 使用POI解析xls
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<PasswordManager> pwms = new ArrayList<>();
            for (Row row : sheet) {
                int rowNum = row.getRowNum();
                // 第一行标题忽略
                if (rowNum == 0) {
                    continue;
                }

                String[] p = new String[4];

                for (int i = 0; i < 4; i++) {
                    if (row.getCell(i) != null){
                        row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                        p[i] = encryptUtils.aesEncrypt(row.getCell(i).getStringCellValue());
                    }
                }

                // 将账号信息装进regions 开启一个事务保存
                PasswordManager pwm = new PasswordManager(loginUser,p[0],p[1],p[2],p[3]);
                pwms.add(pwm);
            }

            apmService.saveBatch(pwms);

        } catch (Exception e) {
            System.out.println(e);
            flag = "0";
        }

        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);

        return NONE;
    }

}
