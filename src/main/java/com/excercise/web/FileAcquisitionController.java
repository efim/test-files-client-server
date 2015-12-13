package com.excercise.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.excercise.filemanager.FileManager;

@Controller
@RequestMapping("/upload")
public class FileAcquisitionController {
	FileManager fileManager;

	@Autowired
	public FileAcquisitionController(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@RequestMapping(method = POST)
	public String upload(@RequestParam("file") MultipartFile file, Model model) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				fileManager.add(file.getOriginalFilename(), bytes);
				model.addAttribute("uploadSuccessfull", true);
				return "homepage";
			} catch (Exception e) {
				model.addAttribute("uploadSuccessfull", false);
				model.addAttribute("errorMessage"
						, "You failed to upload " + file.getName() + " => " + e.getMessage());
				return "homepage";
			}
		} else {
			model.addAttribute("uploadSuccessfull", false);
			model.addAttribute("errorMessage"
					, "You failed to upload " + file.getName() + " because the file was empty.");
			return "homepage";
		}
	}
}
