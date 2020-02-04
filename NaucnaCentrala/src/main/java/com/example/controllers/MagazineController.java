package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.camunda.dto.FormFieldsDto;
import com.example.dto.MagazineDto;
import com.example.services.MagazineService;

import com.example.model.Magazine;

@RestController
@RequestMapping(value = "api/magazine")
public class MagazineController {

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	MagazineService magazineService;
	
	@GetMapping("/allMagazines")
	public ResponseEntity<List<MagazineDto>> getAllMagazines(){
		
		List<Magazine> magazines = magazineService.getAll();
		List<MagazineDto> magazinesDTO = new ArrayList<>();
		
		for(Magazine c : magazines){
			magazinesDTO.add(magazineService.mapToDTO(c));
		}
		return new ResponseEntity<>(magazinesDTO, HttpStatus.OK);
	}
	
	@GetMapping(path = "/startMagazineProcess/magazineId", produces = "application/json")
    public @ResponseBody FormFieldsDto get(@PathVariable String magazineId) {

		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Article");
		runtimeService.setVariable(pi.getProcessInstanceId(), "magazineId", magazineId);
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	
	@RequestMapping(value = "/{magazineId}", method = RequestMethod.GET)
	public ResponseEntity<MagazineDto> getMagazine(@PathVariable Long magazineId) {
		Magazine c = magazineService.getMagazine(magazineId);
		if (c == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new MagazineDto(c), HttpStatus.OK);
	}

	@RequestMapping(value = "/addMagazine", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MagazineDto> addMagazine(@RequestBody MagazineDto magazineDto) {
		Magazine c = magazineService.mapDTO(magazineDto);
		magazineService.addMagazine(c);
		return new ResponseEntity<>(new MagazineDto(c), HttpStatus.CREATED);
	}
}