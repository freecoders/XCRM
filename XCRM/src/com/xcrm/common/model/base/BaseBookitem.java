package com.xcrm.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBookitem<M extends BaseBookitem<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setProduct(java.lang.Integer product) {
		set("product", product);
	}

	public java.lang.Integer getProduct() {
		return get("product");
	}

	public void setPrice(java.lang.Integer price) {
		set("price", price);
	}

	public java.lang.Integer getPrice() {
		return get("price");
	}

	public void setNum(java.lang.Integer num) {
		set("num", num);
	}

	public java.lang.Integer getNum() {
		return get("num");
	}

	public void setUser(java.lang.Integer user) {
		set("user", user);
	}

	public java.lang.Integer getUser() {
		return get("user");
	}

	public void setDate(java.util.Date date) {
		set("date", date);
	}

	public java.util.Date getDate() {
		return get("date");
	}

	public void setStatus(java.lang.Boolean status) {
		set("status", status);
	}

	public java.lang.Boolean getStatus() {
		return get("status");
	}

	public void setPrdattrs(java.lang.String prdattrs) {
		set("prdattrs", prdattrs);
	}

	public java.lang.String getPrdattrs() {
		return get("prdattrs");
	}

	public void setContract(java.lang.Integer contract) {
		set("contract", contract);
	}

	public java.lang.Integer getContract() {
		return get("contract");
	}

	public void setContact(java.lang.Integer contact) {
		set("contact", contact);
	}

	public java.lang.Integer getContact() {
		return get("contact");
	}

}