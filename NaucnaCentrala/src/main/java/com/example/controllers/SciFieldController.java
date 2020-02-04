package com.example.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.form.FormType;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.camunda.dto.FieldDto;
import com.example.camunda.dto.FormFieldsDto;
import com.example.dto.SciFieldDto;
import com.example.services.SciFieldService;

import com.example.model.ScientificField;

@RestController
@RequestMapping(value = "api/sciAreas")
public class SciFieldController {

	@Autowired
	SciFieldService scientificAreaService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@GetMapping("/allSciAreas")
	public ResponseEntity<List<SciFieldDto>> getAllSciAreas(){
		
		List<ScientificField> scientificAreas = scientificAreaService.getAll();
		List<SciFieldDto> scientificAreasDTO = new ArrayList<>();
		
		for(ScientificField c : scientificAreas){
			scientificAreasDTO.add(scientificAreaService.mapToDTO(c));
		}
		
		return new ResponseEntity<>(scientificAreasDTO, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SciFieldDto> getSciArea(@PathVariable Long id) {
		
		ScientificField c = scientificAreaService.getScientificArea(id);
		if (c == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new SciFieldDto(c), HttpStatus.OK);
	}

	@RequestMapping(value = "/addSciArea", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<SciFieldDto> addSciArea(@RequestBody SciFieldDto scientificAreaDTO) {
		
		ScientificField c = scientificAreaService.mapDTO(scientificAreaDTO);
		scientificAreaService.addScientificArea(c);

		return new ResponseEntity<>(new SciFieldDto(c), HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/getSciFieldsForRegister/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTaskForm(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		FormField enumField = properties.get(0);
		EnumFormType enumFormType = (EnumFormType) enumField.getType();
		Map<String, String> values = enumFormType.getValues();
		for(ScientificField c : scientificAreaService.getAll()){
			values.put(c.getId().toString(), c.getName());
		}
		
        return new FormFieldsDto(task.getId(),task.getProcessInstanceId(), properties);
    }
	

}