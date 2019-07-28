package com.wenchao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wenchao
 * @date 2019/7/27.
 * @time 23:07
 * description：
 */
@Target(ElementType.TYPE)//声明这个注解是放在什么上面
@Retention(RetentionPolicy.CLASS)//声明这个注解的生命周期
public @interface BindPath {
    String value();
}
