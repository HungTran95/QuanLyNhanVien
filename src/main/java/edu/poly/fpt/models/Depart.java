package edu.poly.fpt.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.poly.fpt.models.Staff;

@Entity
@Table(name="departs")
public class Depart implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "depart", cascade = CascadeType.ALL)
	private Set<Staff> staffs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	public Depart(Integer id, String name, Set<Staff> staffs) {
		super();
		this.id = id;
		this.name = name;
		this.staffs = staffs;
	}

	public Depart() {
		super();
	}
	
}
