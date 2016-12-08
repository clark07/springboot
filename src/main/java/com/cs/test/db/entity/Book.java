package com.cs.test.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/12/7.
 */
@Entity
public class Book {
	private int id;
	private String name;
	private String description;
	private String baseUrl;
	private String indexUrl;
	private int lastestChapterId;
	private String lastestChapterName;
	private Timestamp lastedSpiderTime;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int spiderOpen;
	private int status;
	private String author;

	@Id
	@GeneratedValue
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "base_url")
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Basic
	@Column(name = "index_url")
	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	@Basic
	@Column(name = "lastest_chapter_id")
	public int getLastestChapterId() {
		return lastestChapterId;
	}

	public void setLastestChapterId(int lastestChapterId) {
		this.lastestChapterId = lastestChapterId;
	}

	@Basic
	@Column(name = "lastest_chapter_name")
	public String getLastestChapterName() {
		return lastestChapterName;
	}

	public void setLastestChapterName(String lastestChapterName) {
		this.lastestChapterName = lastestChapterName;
	}

	@Basic
	@Column(name = "lasted_spider_time")
	public Timestamp getLastedSpiderTime() {
		return lastedSpiderTime;
	}

	public void setLastedSpiderTime(Timestamp lastedSpiderTime) {
		this.lastedSpiderTime = lastedSpiderTime;
	}

	@Basic
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Basic
	@Column(name = "spider_open")
	public int getSpiderOpen() {
		return spiderOpen;
	}

	public void setSpiderOpen(int spiderOpen) {
		this.spiderOpen = spiderOpen;
	}

	@Basic
	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Basic
	@Column(name = "author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Book book = (Book) o;

		if (id != book.id)
			return false;
		if (lastestChapterId != book.lastestChapterId)
			return false;
		if (spiderOpen != book.spiderOpen)
			return false;
		if (status != book.status)
			return false;
		if (name != null ? !name.equals(book.name) : book.name != null)
			return false;
		if (description != null ? !description.equals(book.description) : book.description != null)
			return false;
		if (baseUrl != null ? !baseUrl.equals(book.baseUrl) : book.baseUrl != null)
			return false;
		if (indexUrl != null ? !indexUrl.equals(book.indexUrl) : book.indexUrl != null)
			return false;
		if (lastestChapterName != null ? !lastestChapterName
				.equals(book.lastestChapterName) : book.lastestChapterName != null)
			return false;
		if (lastedSpiderTime != null ? !lastedSpiderTime.equals(book.lastedSpiderTime) : book.lastedSpiderTime != null)
			return false;
		if (createTime != null ? !createTime.equals(book.createTime) : book.createTime != null)
			return false;
		if (updateTime != null ? !updateTime.equals(book.updateTime) : book.updateTime != null)
			return false;
		if (author != null ? !author.equals(book.author) : book.author != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (baseUrl != null ? baseUrl.hashCode() : 0);
		result = 31 * result + (indexUrl != null ? indexUrl.hashCode() : 0);
		result = 31 * result + lastestChapterId;
		result = 31 * result + (lastestChapterName != null ? lastestChapterName.hashCode() : 0);
		result = 31 * result + (lastedSpiderTime != null ? lastedSpiderTime.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		result = 31 * result + spiderOpen;
		result = 31 * result + status;
		result = 31 * result + (author != null ? author.hashCode() : 0);
		return result;
	}
}
