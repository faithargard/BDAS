package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping(value = "/gateway")
public class GatewayController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	Environment env;

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String getData() {
		System.out.println("Return requested data");
		return "Gateway hello";
	}

	@RequestMapping(value = "/server-data", method = RequestMethod.GET)
	public String getDataFromServer() {
		System.out.println("Get server data from gateway");
		try {
			String msEndpoint = env.getProperty("endpoint.server");
			System.out.println("Endpoint name : [" + msEndpoint + "]");
			return restTemplate.getForObject(new
					URI(Objects.requireNonNull(msEndpoint)), String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Exception occurred";
	}
}
