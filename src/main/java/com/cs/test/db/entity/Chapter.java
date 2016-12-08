package com.cs.test.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/12/7.
 */
@Entity
public class Chapter {
	private int id;
	private String name;
	private int bookId;
	private String bookName;
	private String content;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String sourceUrl;
	private int retryStatus;
	private int retryCount;

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
	@Column(name = "book_id")
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Basic
	@Column(name = "book_name")
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Basic
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	@Column(name = "source_url")
	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@Basic
	@Column(name = "retry_status")
	public int getRetryStatus() {
		return retryStatus;
	}

	public void setRetryStatus(int retryStatus) {
		this.retryStatus = retryStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Chapter chapter = (Chapter) o;

		if (id != chapter.id)
			return false;
		if (bookId != chapter.bookId)
			return false;
		if (retryStatus != chapter.retryStatus)
			return false;
		if (name != null ? !name.equals(chapter.name) : chapter.name != null)
			return false;
		if (bookName != null ? !bookName.equals(chapter.bookName) : chapter.bookName != null)
			return false;
		if (content != null ? !content.equals(chapter.content) : chapter.content != null)
			return false;
		if (createTime != null ? !createTime.equals(chapter.createTime) : chapter.createTime != null)
			return false;
		if (updateTime != null ? !updateTime.equals(chapter.updateTime) : chapter.updateTime != null)
			return false;
		if (sourceUrl != null ? !sourceUrl.equals(chapter.sourceUrl) : chapter.sourceUrl != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + bookId;
		result = 31 * result + (bookName != null ? bookName.hashCode() : 0);
		result = 31 * result + (content != null ? content.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		result = 31 * result + (sourceUrl != null ? sourceUrl.hashCode() : 0);
		result = 31 * result + retryStatus;
		return result;
	}

	@Basic
	@Column(name = "retry_count")
	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
}
