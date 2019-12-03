package com.hien.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String _id;
	private String name;
	private Date createAt;
	private Date updateAt;

	public Customer() {
		super();
	}

	public Customer(String name, Date createAt, Date updateAt) {
		super();
		this.name = name;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Customer(String _id, String name, Date createAt, Date updateAt) {
		super();
		this._id = _id;
		this.name = name;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "Customer [_id=" + _id + ", name=" + name + ", createAt=" + createAt + ", updateAt=" + updateAt + "]";
	}

}
