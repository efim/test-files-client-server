package com.excercise.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excercise.filemanager.FileManager;

@Controller
@RequestMapping("/delete")
public class FileDeletionController {
	
	FileManager fileManager;
	
	@Autowired
	public FileDeletionController(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	@RequestMapping(method=POST)
	public String delete(@RequestParam("fileId") String fileId, Model model) {
		boolean result = fileManager.remove(fileId);
		
		model.addAttribute("deletionSussesfull", result);
		
		
		return "homepage";
	}
}
