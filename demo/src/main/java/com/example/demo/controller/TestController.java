package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

	@GetMapping
	public String testController() {
		return "Hello World!";
	}

	@GetMapping("/testGetMapping")
	public String testControllerWithPath() {
		return "Hello World! With Path";
	}

	@GetMapping("/{id}")
	public String testContrillerWithPathVariables(@PathVariable(required = false) int id) {
		return "Hello World!" + id;
	}

	@GetMapping("/testRequestParam")
	public String testControllerRequestParam(@RequestParam(required = false)int id) {
		return "Hello World! ID" + id;
	}

	@GetMapping("/testRequestBody")
	public String testContrillerReqeuestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		return "Hello world! ID" + testRequestBodyDTO.getId() + "Message : " + testRequestBodyDTO.getMessage();
	}

	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseDTO");
		ResponseDTO<String> reponse = ResponseDTO.<String>builder().data(list).build();
		return reponse;
	}

	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 400! ");
		ResponseDTO<String> reponse = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.badRequest().body(reponse);
	}

}
