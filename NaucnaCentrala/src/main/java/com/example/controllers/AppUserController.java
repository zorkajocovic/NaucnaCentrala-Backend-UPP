package com.example.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.jvnet.hk2.config.GenerateServiceFromMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.camunda.dto.FieldDto;
import com.example.camunda.dto.LoginDto;
import com.example.camunda.dto.TaskDto;
import com.example.dto.AppUserDto;
import com.example.dto.LoginRequestDto;
import com.example.services.AppUserService;
import com.example.services.SciFieldService;
import com.example.model.Appuser;
import com.example.security.JWTUtils;
import com.example.security.MyUserDetailsService;
import com.example.security.TokenDto;

@RestController
@RequestMapping(value = "api/user")
public class AppUserController {

	@Autowired
	AppUserService userService;
	
	@Autowired
	SciFieldService sciFieldService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
    RuntimeService runtimeService;
	
	@Value("Authorization")
	private String tokenHeader;
	
	@GetMapping("/allUsers")
	public ResponseEntity<List<AppUserDto>> getAllUsers(){
		
		List<Appuser> users = userService.getAll();
		List<AppUserDto> usersDTO = new ArrayList<>();
		for(Appuser c : users){
			usersDTO.add(userService.mapToDTO(c));
		}
		
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AppUserDto> getUser(@PathVariable Long id) {
		Appuser c = userService.getOne(id);
		if (c == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new AppUserDto(c), HttpStatus.OK);
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<AppUserDto> addUser(@RequestBody AppUserDto userDto) {
		Appuser c = userService.mapDTO(userDto);
		userService.addUser(c);
	
		return new ResponseEntity<>(new AppUserDto(c), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
	public void confirmRegistration(@RequestParam("processInstanceId") String processInstanceId) 
	{
		runtimeService.setVariable(processInstanceId, "confirmed", true);
	}
	
	@PostMapping(path = "/addSciFieldForUser/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Object> addSciFieldForUser(@RequestBody List<FieldDto> dto, @PathVariable String taskId) {
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "sciFields", dto);
		//formService.submitTaskForm(taskId, map);
		taskService.complete(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	private HashMap<String, Object> mapListToDto(List<FieldDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FieldDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		return map;
	}
	
	@PostMapping(path = "/tasks/submit/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> submitTask(@RequestBody List<FieldDto> dto, @PathVariable String taskId) {
		
		HashMap<String, Object> map = this.mapListToDto(dto);
		formService.submitTaskForm(taskId, map);
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/login/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> post(@RequestBody LoginRequestDto loginRequestDto, @PathVariable String taskId) {
		
		try{
			  Appuser user = userService.getbyUsername(loginRequestDto.getUsername());
			  if(user != null) {

				  TokenDto tokenDto = userService.generateToken(user.getUsername());

		            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		    		String processInstanceId = task.getProcessInstanceId();
		    		runtimeService.setVariable(processInstanceId, "exists", true);
		    		runtimeService.setVariable(processInstanceId, "authorId", user.getUsername());
		    		taskService.complete(taskId);    		
					return new ResponseEntity<>(tokenDto, HttpStatus.OK);
			  }
			  else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			  }			  
			} 
			catch(AuthenticationException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
    }
	
	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<AppUserDto> getCurrentUser() {
		
		Appuser currentUser = userService.getCurrentUser();
		AppUserDto userDto = new AppUserDto(currentUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> signout() {
		
        SecurityContextHolder.clearContext();
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	
}
