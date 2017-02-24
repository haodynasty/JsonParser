/*
 * FileName: UserEntity.java
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
 * date     : 2015-4-19 下午4:47:38
 * last modify author :
 * version : 1.0
 */
package com.blakequ.jsonparser.model;


import com.blakequ.parser.JsonParserClass;
import com.blakequ.parser.JsonParserField;

/**
 * @ClassName: UserEntity
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-4-19 下午4:47:38<br>
 *     <b>最后修改时间：</b>2015-4-19 下午4:47:38
 * @version v1.0
 */
@JsonParserClass(parserRoot="entities")
public class UserEntity{
	private double index;
	private int id;
	
	@JsonParserField(defaultValue="1110")
	private Short ids;
	@JsonParserField(praserKey="createdTime", defaultValue="110")
	private long time;
	@JsonParserField(praserKey="descript")
	private String descripte;
	@JsonParserField(praserKey="isDeleted", defaultValue="1")
	private boolean isDelete;
	@JsonParserField(defaultValue="未知")
	private String name;
	@JsonParserField(defaultValue="0")
	private Long page;
	@JsonParserField(defaultValue="")
	private String pic;
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getDescripte() {
		return descripte;
	}
	public void setDescripte(String descripte) {
		this.descripte = descripte;
	}
	public double getIndex() {
		return index;
	}
	public void setIndex(double index) {
		this.index = index;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	@Override
	public String toString() {
		return "UserEntity [time=" + time + ", descripte=" + descripte
				+ ", index=" + index + ", id=" + id + ", isDelete=" + isDelete
				+ ", name=" + name + ", page=" + page + ", pic=" + pic + ",ids="+ids+"]";
	}

}
