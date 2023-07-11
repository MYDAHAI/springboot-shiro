package com.yr.springbootshiro.filter;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Set;


public class InitFilter {

    // 注意/r/n前不能有空格
    private static final String CRLF = "\r\n";


    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer();
        sb.append(getFixedAuthRule());//固定权限，采用读取配置文件
        System.out.println("ini : " + getFixedAuthRule());
        return sb.toString();
    }

    /**
     * 从配额文件获取固定权限验证规则串
     */
    private String getFixedAuthRule(){
        String fileName = "shiro_base_auth.ini";
        ClassPathResource cp = new ClassPathResource(fileName);
        INI4j ini = null;
        try {
            ini = new INI4j(cp.getFile());
        } catch (IOException e) {
            System.err.println(getClass().toString() + e + "加载文件出错。file:[%s]" + fileName);
        }
        String section = "base_auth";
        Set<String> keys = ini.get(section).keySet();
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = ini.get(section, key);
            sb.append(key).append(" = ")
                    .append(value).append(CRLF);
        }

        return sb.toString();

    }
}
