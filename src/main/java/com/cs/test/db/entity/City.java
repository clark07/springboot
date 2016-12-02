package com.cs.test.db.entity;

import javax.persistence.*;

/**
 * Created by admin on 2016/12/2.
 */
@Entity
public class City {
	private Long id;
	private String name;
	private String state;
	private String country;
	private String map;

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Basic
	@Column(name = "country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Basic
	@Column(name = "map")
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		City city = (City) o;

		if (id != null ? !id.equals(city.id) : city.id != null)
			return false;
		if (name != null ? !name.equals(city.name) : city.name != null)
			return false;
		if (state != null ? !state.equals(city.state) : city.state != null)
			return false;
		if (country != null ? !country.equals(city.country) : city.country != null)
			return false;
		if (map != null ? !map.equals(city.map) : city.map != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (map != null ? map.hashCode() : 0);
		return result;
	}
}
