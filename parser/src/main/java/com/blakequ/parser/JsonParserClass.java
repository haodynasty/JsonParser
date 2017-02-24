/*
 * FileName: JsonParserClass.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : service@plusub.com
 * date     : 2015-4-19 上午11:15:14
 * last modify author :
 * version : 1.0
 */
package com.blakequ.parser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON字符串自动解析为类，定义了类注解
 * <br><b>注意：必须自动实现所有变量的get和set方法
 * @ClassName: JsonParserClass
 * @Description: TODO
 * @author qh@plusub.com
 * @date 2015-4-19 上午11:15:29
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonParserClass {

	/**
	 * 是否解析对象为数组，默认false
	 */
	boolean isList() default false;
	
	/**
	 * 解析的对象所在JSON字符串的根键值，如 "entities": [{...}{...}]，根键值为entities
	 * <br>如果没有则不设置
	 */
	String parserRoot();

	/**
	 * 是否有page信息，如果有则需要设置pageKeyStr，表示其根键值
	 */
	boolean isHasPage() default false;
	
	/**
	 * 如果有page信息，则page实体变量的名字
	 */
	String pageFieldStr() default "";
}
