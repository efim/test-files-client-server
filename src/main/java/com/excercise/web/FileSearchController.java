package com.excercise.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excercise.filemanager.FileManager;

@Controller
@RequestMapping("/search")
public class FileSearchController {
	FileManager fileManager;
	
	@Autowired
	public FileSearchController(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	@RequestMapping(method=GET)
	public String search(@RequestParam("fileName") String fileName, Model model) {
		Map<String, String> searchResult = fileManager.find(fileName);
		
		model.addAttribute("resultList", searchResult);
		
		return "searchResults";
	}
	
}
