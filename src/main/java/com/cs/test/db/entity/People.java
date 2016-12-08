package com.cs.test.db.entity;

import javax.persistence.*;

/**
 * Created by admin on 2016/12/5.
 */
@Entity
public class People {
	private Integer id;
	private String name;
	private String city;
	private Integer age;

	@Id
	@GeneratedValue
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
	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Basic
	@Column(name = "age")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		People people = (People) o;

		if (id != null ? !id.equals(people.id) : people.id != null)
			return false;
		if (name != null ? !name.equals(people.name) : people.name != null)
			return false;
		if (city != null ? !city.equals(people.city) : people.city != null)
			return false;
		if (age != null ? !age.equals(people.age) : people.age != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (age != null ? age.hashCode() : 0);
		return result;
	}
}
