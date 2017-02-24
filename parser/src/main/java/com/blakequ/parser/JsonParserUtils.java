/*
 * FileName: JsonParserUtils.java
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
 * date     : 2015-4-19 上午10:51:14
 * last modify author :
 * version : 1.0
 */
package com.blakequ.parser;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 基于注解的JSON自动解析工具类
 * @ClassName: JsonParserUtils
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-4-19 上午10:51:14<br>
 *     <b>最后修改时间：</b>2015-4-19 上午10:51:14
 * @version v1.0
 */
public class JsonParserUtils {

	private static final String TAG = "JsonParserUtils";
	private static boolean showLog = false;
	
	/**
	 * 解析日志打印开关
	 * <p>Title: setPrintSwitch
	 * <p>Description: 
	 * @param isOpen true则打开
	 */
	public static void setLogSwitch(boolean isOpen){
		showLog = isOpen;
	}
	
	/**
	 * 自动解析JSON字符串到实体对象objEntity
	 * <p>Title: initEntityParser
	 * <p>Description: 
	  * @param className 解析对应的实体对象
	 * @param jsonString JSON字符串
	 * @return
	 * @throws Exception
	 */
	public static Object initEntityParser(Class className, String jsonString) throws Exception{
        Object obj = getInstance(className.getName());
		JsonParserClass jsonClass =  obj.getClass().getAnnotation(JsonParserClass.class);
		boolean isClassList = false;
		if (jsonClass != null) {
			isClassList = jsonClass.isList();
		}
		return initEntityParser(className, jsonString, isClassList);
	}
	
	/**
	 * 自动解析JSON字符串到实体对象objEntity
	 * <p>Title: initEntityParser
	 * <p>Description: 
	 * @param className 解析对应的实体对象
	 * @param jsonString JSON字符串
	 * @param isParserList 是否将类className解析为一个数组（如果使用了@JsonParserClass则忽略）
	 * @throws Exception
	 */
	public static Object initEntityParser(Class className, String jsonString, boolean isParserList) throws Exception{
        JSONObject jo = new JSONObject(jsonString);
        Object obj = getInstance(className.getName());
        
        JsonParserClass jsonClass =  obj.getClass().getAnnotation(JsonParserClass.class);
        String rootKey = "";
        boolean isHasPage = false;
        String pageKey = "";
        if (jsonClass != null) {
        	rootKey = jsonClass.parserRoot();
        	isHasPage = jsonClass.isHasPage();
        	pageKey = jsonClass.pageFieldStr();
		}
        Object result = null;
        
        if (!isParserList) {
        	if (isEmpty(rootKey)) {
        		result = parserField(obj, jo);
			}else{
				JSONObject json = JSONUtils.getJSONObject(jo, rootKey, null);
				if (json != null) {
					result = parserField(obj, json);
				}
			}
		}else{
			Object pageObj = null;
			//解析page
			if (isHasPage) {
				Field field;
				try {
					field = obj.getClass().getDeclaredField(pageKey);
					JsonParserField jsonField = field.getAnnotation(JsonParserField.class);
					String key = jsonField.praserKey();
					if (isEmpty(key)) {
						key = field.getName();
					}
					JSONObject pageJO = JSONUtils.getJSONObject(jo, key, null);
					if (pageJO != null) {
						pageObj = getPageInfo(pageJO, field);
						setFieldValue(obj, field, pageObj);
					}
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					if (showLog) {
						Log.e("JsonParserUtils", "没有找到Field：" + pageKey);
						e.printStackTrace();
					}
				}
			}
			
			//获取数组
			JSONArray ja = JSONUtils.getJSONArray(jo, rootKey, null);
			if (ja != null && ja.length() > 0) {
				int size = ja.length();
				Object[] data = new Object[size];
				//数组解析
				for (int index = 0; index < size; index++) {
					Object oj = parserField(getInstance(className.getName()), ja.getJSONObject(index));
					
					//设置page到每个对象
					if (isHasPage && pageObj != null) {
						try {
							setFieldValue(oj, oj.getClass().getDeclaredField(pageKey), pageObj);
						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							if (showLog) {
								e.printStackTrace();
							}
						}
					}
					
					data[index] = oj;
				}
				result = Arrays.asList(data);
			}else{
				if (showLog) {
					Log.i(TAG, "JSONArray is Empty "+rootKey);
				}
			}
		}
		
        return result;
	}
	
	/**
	 * 解析页码信息，只是针对返回列表的JSON数据
	 * <p>Title: getPageInfo
	 * <p>Description: 
	 * @param jo
	 * @param field
	 * @return
	 */
	private static Object getPageInfo(JSONObject jo, Field field){
		Object pageObj = null;
		try {
			pageObj = parserField(getInstance(field.getType().getName()), jo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (showLog) {
				e.printStackTrace();
			}
			return pageObj;
		}
		return pageObj;
	}
	
	
	/**
	 * 解析单个实体对象
	 * <p>Title: parserField
	 * <p>Description: 
	 * @param obj
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	private static Object parserField(Object obj, JSONObject jsonObj) throws Exception{
		Field[] fields = obj.getClass().getDeclaredFields();
		 if (fields != null && fields.length > 0) {
	            for (Field field : fields) {
	            	JsonParserField jsonField = field.getAnnotation(JsonParserField.class);
	            	
	            	//有设置JsonParserField的字段
	                if (jsonField != null) {
	                    String keyName = jsonField.praserKey(); //json键
	                    String defaultValue = jsonField.defaultValue(); //如果没有则设置默认值
	                    boolean isList = jsonField.isList();
	                    
	                  //是否有别名
                		String name = "";
                		if (isEmpty(keyName)) {
                			name = field.getName();
						}else{
							name = keyName;
						}
                		
	                    try {
	                        field.setAccessible(true);
	                        //域是否存在
	                        if (jsonObj.has(name)) {
	                        	if (!isList) { //域不为数组
	                        		setSingleValue(obj, field, jsonObj, name);
								}else{  //域为数组
									Class listCls = jsonField.classType();
									if (listCls.equals(Object.class)) {
										if (showLog) Log.e(TAG, "未设置数组属性" + field.getName() + "的classType数组类型，无法解析：" + keyName);
									}
									JSONArray ja = null;
									if (isEmpty(keyName)) {
										ja = JSONUtils.getJSONArray(jsonObj, field.getName(), null);
									}else{
										ja = JSONUtils.getJSONArray(jsonObj, keyName, null);
									}
									if (ja != null) {
										//数组长度
										int size = ja.length();
										Object[] data = new Object[size];
										
										//数组解析
										for (int index = 0; index < size; index++) {
											Object oj = parserField(getInstance(listCls.getName()), ja.getJSONObject(index));
											data[index] = oj;
										}
										
										//设置数据
										Object listObj = field.getType();
										if (listObj.equals(List.class)) {
											setFieldValue(obj, field, Arrays.asList(data));
										}else{
											setFieldValue(obj, field, data);
										}
									}
								}
							}else{
								setFieldValue(obj, field, defaultValue);
							}
	                    } catch (Exception e) {
	                    	throw new RuntimeException("parser "+obj.getClass().getName()+" error! \n"+e.getMessage());
	                    }
	                }else{ //没有设置JsonParserField的字段，全部采用默认
	                	setSingleValue(obj, field, jsonObj, field.getName());
	                }
	            }
	        }
		return obj;
	}
	
	/**
	 * 保存单个对象到域
	 * <p>Title: setSingleValue
	 * <p>Description: 
	 * @param object 实体对象
	 * @param field 实体的域
	 * @param jsonObj JSON对象
	 * @param name 待解析的JSON键
	 * @throws Exception
	 */
	private static void setSingleValue(Object object, Field field, JSONObject jsonObj, String name) throws Exception{
		//判断是否为基本类型
		if (!isBaseDataType(field.getType())) {
			JSONObject jo = JSONUtils.getJSONObject(jsonObj, name, null);
			if (jo != null) {
				Object result = parserField(getInstance(field.getType().getName()), jo);
				if (result != null) {
					setFieldValue(object, field, result);
				}
			}
		}else{
			setFieldValue(object, field, JSONUtils.get(jsonObj, name, null));
		}
	}
	
	/**
	 * 反射实例化类
	 * <p>Title: getInstance
	 * <p>Description: 
	 * @param className
	 * @return
	 * @throws Exception
	 */
	private static Object getInstance(String className) throws Exception{
		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("getInstance init class "+className+" error! \n"+e.getMessage());
		}
		return obj;
	}
	
	/**
	 * 是否为空字符串
	 * <p>Title: isEmpty
	 * <p>Description: 
	 * @param value
	 * @return
	 */
	private static boolean isEmpty(String value){
		if (value == null || value.equals("")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 设置域
	 * <p>Title: setFieldValue
	 * <p>Description: 
	 * @param object 域所在实体对象
	 * @param field 待设置的域
	 * @param value 待设置的值
	 */
	private static void setFieldValue(Object object, Field field, Object value){
		Object ov = null;
		try {
			ov = getType(field, value, field.getName());
			field.set(object, ov);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			if (showLog) {
				e.printStackTrace();
			}
			
			//如果field无法访问，就调用set方法进行设置
			try {
				Method m = object.getClass().getMethod(getSetMethodName(field), field.getType());
				if (m != null) {
					m.invoke(object, ov);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				if (showLog) {
					Log.e(TAG, "set field value fail field:"+field.getName()+" method:"+getSetMethodName(field));
					e1.printStackTrace();
				}
			} 
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			if (showLog) {
				e.printStackTrace();
			}
		} catch (Exception e){
			if (showLog) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断类是否基本类型
	 * <p>Title: isWrapClass
	 * <p>Description: 
	 * @param clazz
	 * @return
	 */
	private static boolean isBaseDataType(Class clazz) {
		return (clazz.isPrimitive() || clazz.equals(String.class)
				|| clazz.equals(Integer.class) 
				|| clazz.equals(Boolean.class) 
				|| clazz.equals(Long.class)
				|| clazz.equals(Double.class)
				|| clazz.equals(Float.class) 
				|| clazz.equals(Short.class) 
				|| clazz.equals(Character.class)
				|| clazz.equals(Date.class))
				|| clazz.equals(Byte.class)
				|| clazz.equals(Void.class)
				|| clazz.equals(BigDecimal.class)
				|| clazz.equals(BigInteger.class);
	}
	
	/**
	 * 类型转换
	 * <p>Title: getType
	 * <p>Description: 
	 * @param field 待转换类型
	 * @param defaultValue 值类型(在Json转换中都是String类型)
	 * @return 将defaultValue转换为obj的类型
	 */
	private static Object getType(Field field, Object defaultValue, String fieldName){
		Object value = defaultValue;
		if (showLog) {
			Log.i(TAG, "getType:" + field.getName() + " " + field.getType().getName() + " " + " " + defaultValue);
		}
		if (defaultValue == null) {
			return value;
		}
		
		String type = field.getType().getName();
		Class clazz = field.getType();
		try {
			if (isBaseDataType(field.getType())) {
				String str = defaultValue+"";
				if (clazz.equals(String.class)) {
					value = defaultValue;
				}else if(clazz.equals(Integer.class) || type.equals("int")){
					value = Integer.parseInt(str);
				}else if(clazz.equals(Boolean.class) || type.equals("boolean")){
					String defaultStr = str;
					String result = defaultStr.toLowerCase();
					if (result.equals("true")) {
						value = true;
					}else if (result.equals("false")) {
						value = false;
					}else{
						value = Integer.parseInt(result) > 0 ? true:false;
					}
				}else if(clazz.equals(Double.class) || type.equals("double")){
					value = Double.parseDouble(str);
				}else if(clazz.equals(Float.class) || type.equals("float")){
					value = Float.parseFloat(str);
				}else if(clazz.equals(Short.class) || type.equals("short")){
					value = Short.parseShort(str);
				}else if(clazz.equals(Long.class) || type.equals("long")){
					value = Long.parseLong(str);
				}else if(clazz.equals(Byte.class) || type.equals("byte")){
					value = Byte.parseByte(str);
				}
			}else{ //非基本类型
				if (showLog) {
					Log.i(TAG, "不是基本类型, 值为："+defaultValue);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			if (showLog) {
				Log.e(TAG, "转换域"+fieldName+"错误，从类型String-->"+field.getType().getName()+", 值为"+value);
				e.printStackTrace();
			}
			return value;
		}
		return value;
	}
	
	/**
	 * 获取set方法名
	 * <p>Title: getSetMethodName
	 * <p>Description: 
	 * @param field
	 * @return
	 */
	private static String getSetMethodName(Field field){
		Class clazz = field.getType();
		String name = field.getName();
		StringBuilder sb = new StringBuilder();
		//boolean 类型自动生成set方法的三种情况 
		//private boolean ishead; setIshead
		//private boolean isHead; setHead
		//private boolean head; setHead
		if(clazz.equals(Boolean.class) || clazz.getName().equals("boolean")){
			sb.append("set");
			if (name.startsWith("is")) {
				if (name.length() > 2) {
					//private boolean isHead; setHead
					if (Character.isUpperCase(name.charAt(2))) {
						sb.append(name.substring(2));
					}else{//private boolean ishead; setIshead
						sb.append(name.toUpperCase().charAt(0));
						sb.append(name.substring(1));
					}
				}else{
					sb.append(name.toUpperCase().charAt(0));
					sb.append(name.substring(1));
				}
			}else{//private boolean head; setHead
				sb.append(name.toUpperCase().charAt(0));
				sb.append(name.substring(1));
			}
		}else{
			sb.append("set");
			sb.append(name.toUpperCase().charAt(0));
			sb.append(name.substring(1));
		}
		return sb.toString();
	}
}
