package com.y2t.akeso.common.sql;
/** 
 * @author 作者： weiguiyu
 * @email E-mail: weiguiyu@hongrongtech.com
 * @date 创建时间：2017年6月7日 下午4:37:25 
 * @version 1.0 
 * */

import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单复合查询
 * @author admin
 *
 */
public class SimpleSelectLangDriver extends XMLLanguageDriver implements LanguageDriver {
    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");
    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<trim  suffixOverrides=\",\">");
            sb.append("<where>");
            for (Field field : parameterType.getDeclaredFields()) {
            	// 排除被Invisble修饰的变量
                if (!field.isAnnotationPresent(Invisible.class)) {
                	//判断是否是根据时间查询
                	if (field.getName().indexOf("Time")!=-1) {
                		String tmp1 = "<if test=\"_field != null\">AND _column "
                				+ "BETWEEN DATE_FORMAT(#{_field}, '%Y-%m-%d 00:00:00')"
                				+ " AND DATE_FORMAT(#{_field}, '%Y-%m-%d 23:59:59')</if>";                	 
                		sb.append(tmp1.replaceAll("_field", field.getName()).replaceAll("_column",
                				CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
                	}else {
                		String tmp = "<if test=\"_field != null and _field!=''\">AND _column=#{_field}</if>";
                		sb.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column",
                				CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
                	}
                }
            }

            sb.append("</where>");
            sb.append("</trim>");
            script = matcher.replaceAll(sb.toString());
            script = "<script>" + script + "</script>";
        }
        return super.createSqlSource(configuration, script, parameterType);
    }
}
