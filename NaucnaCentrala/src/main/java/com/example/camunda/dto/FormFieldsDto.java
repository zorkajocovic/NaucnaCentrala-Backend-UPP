package com.example.camunda.dto;

import java.io.Serializable;
import java.util.List;

import org.camunda.bpm.engine.form.FormField;

public class FormFieldsDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String taskId;
	
	private List<FormField> formFields;
	
	private String processInstanceId;
	
	public FormFieldsDto(){
		super();
	}
	
	public FormFieldsDto(String taskId, String processInstanceId, List<FormField> formFields) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public List<FormField> getFormField() {
		return formFields;
	}
	
	public void setFormField(List<FormField> formFields) {
		this.formFields = formFields;
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}