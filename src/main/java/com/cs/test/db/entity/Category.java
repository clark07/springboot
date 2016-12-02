package com.cs.test.db.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/12/2.
 */
@Entity
public class Category {
	private Integer id;
	private String name;
	private Integer parentId;
	private String categoryIdPath;
	private Integer sort;
	private Short depth;
	private Timestamp createTime;
	private String imageUrl;
	private String imageUrl2;
	private Integer discountPercent;

	@Id
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	@Column(name = "parent_id")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Basic
	@Column(name = "category_id_path")
	public String getCategoryIdPath() {
		return categoryIdPath;
	}

	public void setCategoryIdPath(String categoryIdPath) {
		this.categoryIdPath = categoryIdPath;
	}

	@Basic
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Basic
	@Column(name = "depth")
	public Short getDepth() {
		return depth;
	}

	public void setDepth(Short depth) {
		this.depth = depth;
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
	@Column(name = "image_url")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Basic
	@Column(name = "image_url2")
	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	@Basic
	@Column(name = "discount_percent")
	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Category category = (Category) o;

		if (id != null ? !id.equals(category.id) : category.id != null)
			return false;
		if (name != null ? !name.equals(category.name) : category.name != null)
			return false;
		if (parentId != null ? !parentId.equals(category.parentId) : category.parentId != null)
			return false;
		if (categoryIdPath != null ? !categoryIdPath.equals(category.categoryIdPath) : category.categoryIdPath != null)
			return false;
		if (sort != null ? !sort.equals(category.sort) : category.sort != null)
			return false;
		if (depth != null ? !depth.equals(category.depth) : category.depth != null)
			return false;
		if (createTime != null ? !createTime.equals(category.createTime) : category.createTime != null)
			return false;
		if (imageUrl != null ? !imageUrl.equals(category.imageUrl) : category.imageUrl != null)
			return false;
		if (imageUrl2 != null ? !imageUrl2.equals(category.imageUrl2) : category.imageUrl2 != null)
			return false;
		if (discountPercent != null ? !discountPercent
				.equals(category.discountPercent) : category.discountPercent != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
		result = 31 * result + (categoryIdPath != null ? categoryIdPath.hashCode() : 0);
		result = 31 * result + (sort != null ? sort.hashCode() : 0);
		result = 31 * result + (depth != null ? depth.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
		result = 31 * result + (imageUrl2 != null ? imageUrl2.hashCode() : 0);
		result = 31 * result + (discountPercent != null ? discountPercent.hashCode() : 0);
		return result;
	}
}
