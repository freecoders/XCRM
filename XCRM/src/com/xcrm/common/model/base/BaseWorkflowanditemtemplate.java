package com.xcrm.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseWorkflowanditemtemplate<M extends BaseWorkflowanditemtemplate<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setWorkflowtemplate(java.lang.Integer workflowtemplate) {
		set("workflowtemplate", workflowtemplate);
	}

	public java.lang.Integer getWorkflowtemplate() {
		return get("workflowtemplate");
	}

	public void setWorkitemtemplate(java.lang.Integer workitemtemplate) {
		set("workitemtemplate", workitemtemplate);
	}

	public java.lang.Integer getWorkitemtemplate() {
		return get("workitemtemplate");
	}

}
