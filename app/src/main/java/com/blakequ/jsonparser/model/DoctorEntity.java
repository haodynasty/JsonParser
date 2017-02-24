/*
 * FileName: DoctorEntity.java
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
 * date     : 2015-4-19 下午4:53:00
 * last modify author :
 * version : 1.0
 */
package com.blakequ.jsonparser.model;


import com.blakequ.parser.JsonParserClass;
import com.blakequ.parser.JsonParserField;

import java.util.List;


/**
 * @ClassName: DoctorEntity
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-4-19 下午4:53:00<br>
 *     <b>最后修改时间：</b>2015-4-19 下午4:53:00
 * @version v1.0
 */
@JsonParserClass(parserRoot="entities", isList=true)
public class DoctorEntity {

	private int id;
	private int age;
	private String descript;
	private UserEntity hisUser;
	
	@JsonParserField(praserKey="file1Url", defaultValue="")
	private String file;
	@JsonParserField(defaultValue="false")
	private boolean isClose;
	@JsonParserField(praserKey="hisDoctor", isList=true, classType=UserEntity.class)
	private List<UserEntity> hisDoctor;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isClose() {
		return isClose;
	}
	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}
	public UserEntity getHisUser() {
		return hisUser;
	}
	public void setHisUser(UserEntity hisUser) {
		this.hisUser = hisUser;
	}
	
	public List<UserEntity> getHisDoctor() {
		return hisDoctor;
	}
	public void setHisDoctor(List<UserEntity> hisDoctor) {
		this.hisDoctor = hisDoctor;
	}
	@Override
	public String toString() {
		return "DoctorEntity [age=" + age + ", descript=" + descript
				+ ", file=" + file + ", id=" + id + ", isClose=" + isClose
				+ ", hisDoctor=" + hisDoctor + ", hisUser=" + hisUser + "]";
	}

}
