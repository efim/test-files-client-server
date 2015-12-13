package com.excercise.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excercise.filemanager.FileManager;

@Controller
@RequestMapping("/download")
public class FileDispatchController {

	FileManager fileManager;

	@Autowired
	public FileDispatchController(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@RequestMapping(method = GET)
	public String download(@RequestParam("fileId") String fileId, HttpServletResponse response, Model model) {
		byte[] fileToDownload = fileManager.retrieve(fileId);

		if (fileToDownload == null) {
			model.addAttribute("downloadSuccess", false);
			model.addAttribute("error", "FileNotFound");
			return "homepage";
		}

		String fileName = fileManager.getNameById(fileId);

		response.setContentType("application/octet-stream");
		response.setContentLength(fileToDownload.length);

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		response.setHeader(headerKey, headerValue);

		try {
			OutputStream outStream = response.getOutputStream();

			outStream.write(fileToDownload);

			outStream.close();

		} catch (IOException e) {
			model.addAttribute("downloadSuccess", false);
			model.addAttribute("error", "Error while writing  " + fileName + " to output => " + e.getMessage());
			return "homepage";
		}
		
		model.addAttribute("downloadSuccess", true);
		return "homepage";
	}
}
