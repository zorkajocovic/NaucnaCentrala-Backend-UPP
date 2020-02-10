package com.example.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.task.Task;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.camunda.dto.FieldDto;
import com.example.camunda.dto.FormFieldsDto;
import com.example.camunda.dto.TaskDto;
import com.example.dto.ArticleDto;
import com.example.model.Appuser;
import com.example.model.Article;
import com.example.model.ScientificField;
import com.example.services.AppUserService;
import com.example.services.MagazineService;
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
	
	@Autowired
	AppUserService userService;
	
	@Autowired
	MagazineService magService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addArticle(@RequestParam("pdf") MultipartFile file, 
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
		map.put("authorEmail", userService.getCurrentUser().getEmail());

		runtimeService.setVariable(processInstanceId, "newArticle", map);
		runtimeService.setVariable(processInstanceId, "magazineId", magazineId);
		formService.submitTaskForm(taskId, map);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/reupload")
	public ResponseEntity<?> reupload(@RequestParam("pdf") MultipartFile file, 
												@RequestParam("fileName") String fileName,
												@RequestParam("taskId") String taskId) throws IOException {
	
		fileName = fileName.replace(' ', '_');
	    String filePath = uploadService.store(file, fileName);
        filePath = filePath.replace("\\", "/");

        HashMap<String, Object> map = new HashMap<>();
	
		map.put("pdfLocation", filePath);
		map.put("authorEmail", userService.getCurrentUser().getEmail());

		formService.submitTaskForm(taskId, map);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getNew/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArticleDto> get(@PathVariable String taskId) {
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		HashMap<String, Object> map = (HashMap<String, Object>)runtimeService.getVariable(processInstanceId, "newArticle");

		ArticleDto article = new ArticleDto();
		article.setAbstract_(map.get("abstract").toString());
		article.setKeyWords(map.get("keyWords").toString());
		article.setTitle(map.get("title").toString());
		article.setAuthorEmail(map.get("authorEmail").toString());
		
        return new ResponseEntity<ArticleDto>(article,  HttpStatus.OK);
    }
	
	@GetMapping(path = "/numberOfReviewers/{number}/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> get(@PathVariable String taskId, @PathVariable Long number) {
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		HashMap<String, Object> map = new HashMap<>();
		map.put("noReviewers", number);
		
		runtimeService.setVariable(processInstanceId, "noReviewers", number);
		formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/getReviewers/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTaskForm(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		Long magazineId =  (Long) runtimeService.getVariable(task.getProcessInstanceId(), "magazineId");	

		FormField enumField = properties.get(0);
		EnumFormType enumFormType = (EnumFormType) enumField.getType();
		Map<String, String> values = enumFormType.getValues();
		for(Appuser rev : magService.getReviewers(magazineId)){
			values.put(String.valueOf(rev.getId()), rev.getName() + " " + rev.getSurname());
		}
		
        return new FormFieldsDto(task.getId(),task.getProcessInstanceId(), properties);
    }
	
	@RequestMapping(value = "/tasks/submit/{taskId}/{authorEmail}/{decision}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> decideSciArea(@PathVariable String taskId, @PathVariable String authorEmail, @PathVariable boolean decision) {
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("decision", decision);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		if(decision) {
			runtimeService.setVariable(processInstanceId, "isSciAreaGood", true);			
			formService.submitTaskForm(taskId, map);
	        return new ResponseEntity<>(true, HttpStatus.OK);
		}
		else {
			runtimeService.setVariable(processInstanceId, "isSciAreaGood", false);
			runtimeService.setVariable(processInstanceId, "authorEmail", authorEmail);
			formService.submitTaskForm(taskId, map);
	        return new ResponseEntity<>(false, HttpStatus.OK);
		}
    }
	
	@PostMapping(path = "/tasks/submit/{taskId}/{authorEmail}/{goodFormat}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> decidePdf(@PathVariable String taskId, @PathVariable String authorEmail, @PathVariable boolean goodFormat,
										    		 @RequestParam("sec") String sec, @RequestParam("hours") String hours, @RequestParam("min") String min,
										             @RequestParam("days") String days, @RequestParam("months") String months, @RequestParam("comment") String comment) {
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("decision", goodFormat);
		map.put("comment", comment);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();

		if(goodFormat) {
			runtimeService.setVariable(processInstanceId, "goodFormat", true);	
			formService.submitTaskForm(taskId, map);
	        return new ResponseEntity<>(true, HttpStatus.OK);
		}
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        Calendar editorDate = Calendar.getInstance();
	        editorDate.setTime(new Date());
	        editorDate.add(Calendar.SECOND, Integer.parseInt(sec));
	        editorDate.add(Calendar.MINUTE, Integer.parseInt(min));
	        editorDate.add(Calendar.HOUR, Integer.parseInt(hours));
	        editorDate.add(Calendar.DATE, Integer.parseInt(days));
	        editorDate.add(Calendar.MONTH, Integer.parseInt(months));
	        String date = sdf.format(editorDate.getTime()) + "+01";

			runtimeService.setVariable(processInstanceId, "goodFormat", false);	
			runtimeService.setVariable(processInstanceId, "fixFormat", date);	
			runtimeService.setVariable(processInstanceId, "comment", comment);			
			runtimeService.setVariable(processInstanceId, "authorEmail", authorEmail);
			formService.submitTaskForm(taskId, map);
	        return new ResponseEntity<>(false, HttpStatus.OK);
		}
    }
	
	@PostMapping(path = "/review/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> review(@PathVariable String taskId, 
										    		 @RequestParam("accept") String accept, @RequestParam("acceptSmallChanges") String acceptSmallChanges,
										    		 @RequestParam("acceptBigChanges") String acceptBigChanges,
										             @RequestParam("refuse") String refuse, @RequestParam("comment") String comment) {
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("comment", comment);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		runtimeService.setVariable(processInstanceId, "revComment", comment);	
		runtimeService.setVariable(processInstanceId, "accept", accept);	
		runtimeService.setVariable(processInstanceId, "acceptSmallChanges", acceptSmallChanges);	
		runtimeService.setVariable(processInstanceId, "acceptBigChanges", acceptBigChanges);	
		runtimeService.setVariable(processInstanceId, "refuse", refuse);	

		formService.submitTaskForm(taskId, map);
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/decide/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> decide(@PathVariable String taskId, 
										    		 @RequestParam("accept") String accept, @RequestParam("acceptSmallChanges") String acceptSmallChanges,
										    		 @RequestParam("acceptBigChanges") String acceptBigChanges,
										             @RequestParam("refuse") String refuse, @RequestParam("comment") String comment) {
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("comment", comment);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		if(Boolean.parseBoolean(accept)){
			runtimeService.setVariable(processInstanceId, "accepted", true);	
		}
		else if(Boolean.parseBoolean(acceptSmallChanges)){
			runtimeService.setVariable(processInstanceId, "acceptedSmallChanges ", true);	
		}
		else if(Boolean.parseBoolean(acceptBigChanges)){
			runtimeService.setVariable(processInstanceId, "acceptedBigChanges ", true);	
		}
		else if(Boolean.parseBoolean(refuse)){
			runtimeService.setVariable(processInstanceId, "refused ", true);	
		}

		formService.submitTaskForm(taskId, map);
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/addReviewers/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> addReviewers(@PathVariable String taskId, @RequestParam("ids") String ids,
										    		 @RequestParam("sec") String sec, @RequestParam("hours") String hours, @RequestParam("min") String min,
										             @RequestParam("days") String days, @RequestParam("months") String months) {
		
        List<Appuser> reviewers = new ArrayList<>();
        List<String> reviewersUsernames = new ArrayList<>();

		String[] userIds = ids.split(",");
		
        for(String id: userIds) {
            reviewers.add(userService.getOne(Long.parseLong(id)));
        }
        
        for(Appuser user: reviewers) {
        	reviewersUsernames.add(user.getUsername());
        }

		HashMap<String, Object> map = new HashMap<>();

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		runtimeService.setVariable(processInstanceId, "allReviewers", reviewersUsernames);	
		runtimeService.setVariable(processInstanceId, "reviewer", reviewersUsernames.get(0));	

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar editorDate = Calendar.getInstance();
        editorDate.setTime(new Date());
        editorDate.add(Calendar.SECOND, Integer.parseInt(sec));
        editorDate.add(Calendar.MINUTE, Integer.parseInt(min));
        editorDate.add(Calendar.HOUR, Integer.parseInt(hours));
        editorDate.add(Calendar.DATE, Integer.parseInt(days));
        editorDate.add(Calendar.MONTH, Integer.parseInt(months));
        String date = sdf.format(editorDate.getTime()) + "+01";

		runtimeService.setVariable(processInstanceId, "reviewDeadline", date);	
		runtimeService.setVariable(processInstanceId, "assignedUser", userService.getCurrentUser().getUsername());	
		runtimeService.setVariable(processInstanceId, "editorEmail", userService.getCurrentUser().getEmail());	

		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(false, HttpStatus.OK);
		
    }
	
	
}
