/*
 * FileName: BookEntity.java
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
 * date     : 2014-12-29 下午7:11:53
 * last modify author :
 * version : 1.0
 */
package com.blakequ.jsonparser.model;


import com.blakequ.parser.JsonParserClass;
import com.blakequ.parser.JsonParserField;

/**
 * @ClassName: BookEntity
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-29 下午7:11:53<br>
 *     <b>最后修改时间：</b>2014-12-29 下午7:11:53
 * @version v1.0
 */
@JsonParserClass(isList=true, parserRoot="books")
public class BookEntity {

	private String author;
	private String pubdate;
	private String title;
	private String image;
	private String catalog;
	private int pages;
	@JsonParserField(praserKey="alt")
	private String url;
	@JsonParserField(praserKey="summary")
	private String authorInfo;
	private String price;

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPubdata() {
		return pubdate;
	}
	public void setPubdata(String pubData) {
		this.pubdate = pubData;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuthorInfo() {
		return authorInfo;
	}
	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "BookEntity [author=" + author + ", pubData=" + pubdate
				+ ", title=" + title + ", image=" + image + ", catalog="
				+ catalog + ", pages=" + pages + ", url=" + url
				+ ", authorInfo=" + authorInfo + ", price=" + price + "]";
	}
	
}
