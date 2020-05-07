package com.y2t.akeso.common.sql;
/** 
 * @author 作者： weiguiyu
 * @email E-mail: weiguiyu@hongrongtech.com
 * @date 创建时间：2017年6月7日 下午4:37:25 
 * @version 1.0 
 * */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invisible {
}
