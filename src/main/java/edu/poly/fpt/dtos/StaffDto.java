package edu.poly.fpt.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class StaffDto implements Serializable{
	private Long id;
	@NotNull
	@NotEmpty (message = "Họ và tên không được bỏ trống")
	@Length (min = 5, max = 70, message = "Họ và tên phải có tối thiểu 5 kí tự")
	private String name;
	@NotNull(message = "Hãy chọn giới tính")
	private Boolean gender;
	@NotNull(message = "Ngày sinh không được bỏ trống")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthday;
	@NotNull(message="Hãy chọn thư mục ảnh")
	private MultipartFile image;
	@NotNull
	@Email (message = "Hãy nhập đúng định dạng Email")
	private String email;
	@NotNull(message = "Số điện thoại không được bỏ trống")
	@Length(max=10, message="Số điện thoại phải đủ 10 chữ số")
	private String phone;
	@NotNull(message = "Lương không được bỏ trống")
	private Double salary;
	private String notes;
	@NotNull
	private Integer departId;
	@NotNull
	private Integer levell;
	
	public Integer getLevell() {
		return levell;
	}
	public void setLevell(Integer levell) {
		this.levell = levell;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Integer getDepartId() {
		return departId;
	}
	public void setDepartId(Integer departId) {
		this.departId = departId;
	}
	
	
}
