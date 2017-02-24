/*
 * FileName: JsonParser.java
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
 * date     : 2015-4-19 上午10:17:27
 * last modify author :
 * version : 1.0
 */
package com.blakequ.parser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON字符串自动解析为类，定义了域注解
 * @ClassName: JsonParser
 * @Description: TODO JSON自动解析器
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-4-19 上午10:17:27<br>
 *     <b>最后修改时间：</b>2015-4-19 上午10:17:27
 * @version v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonParserField {
	
	/**
	 * 对应待解析JSON中的字段的名，默认为“”
	 */
	String praserKey() default "";
	
	/**
	 * 如果没有找到对于的解析key，则设置为该默认值，默认为“”
	 */
	String defaultValue() default "";
	
	/**
	 * 是否解析为数组，如果为数组，<b>则必须设置属性classType</b>，设置数组的实体对象
	 * <br><b>注意：如果实体为数组对象，必须是List类型
	 */
	boolean isList() default false;
	
	/**
	 * 如果有实体对象，对象的类名
	 */
	Class classType() default Object.class;
}
