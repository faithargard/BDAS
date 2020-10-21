package com.example.demo;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.demo.Constants.HEADER_NAME;

@RestController
public class DataObjectController {

	@RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
	@ResponseBody
	public DataObject findBy(
			@PathVariable long id, HttpServletRequest req, HttpServletResponse res) {
		if (req.getHeader(HEADER_NAME) != null)
			res.addHeader(HEADER_NAME, req.getHeader(HEADER_NAME));

		return new DataObject(id, RandomStringUtils.randomAlphanumeric(10));
	}
}
