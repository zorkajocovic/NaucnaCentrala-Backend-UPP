package com.example.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.camunda.dto.FieldDto;
import com.example.dto.ArticleDto;
import com.example.model.Article;
import com.example.services.UploadFileService;

@RestController
@RequestMapping(value = "api/article")public class ArticleController {
	
	@Autowired
	UploadFileService uploadService;

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@PostMapping("/add")
	public ResponseEntity<ArticleDto> addArticle(@RequestParam("pdf") MultipartFile file, 
												@RequestParam("fileName") String fileName,
												@RequestParam("processInstanceId") String processInstanceId,
												@RequestParam("magazineId") String magazineId,
												@RequestParam("taskId") String taskId,
												@RequestParam("article") String article) throws IOException {
	
		JSONObject jsonArticle = new JSONObject(article);		

		fileName = fileName.replace(' ', '_');
	    String filePath = uploadService.store(file, fileName);
        filePath = filePath.replace("\\", "/");
	    
        HashMap<String, Object> map = new HashMap<>();
		map.put("title", jsonArticle.getString("title"));
		map.put("abstract", jsonArticle.getString("abstract"));
		map.put("keyWords", jsonArticle.getString("keyWords"));
		map.put("pdfLocation", filePath);
		map.put("magazineId", Long.parseLong(magazineId));
	
		runtimeService.setVariable(processInstanceId, "newArticle", map);
		formService.submitTaskForm(taskId, map);
		
		return null;
	}
	
	
	
	
	
	
	
}
