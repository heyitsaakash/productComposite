package com.bbb.composite.product.details.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbb.core.controllers.BaseController;

/**
 * Health Check for Product Details Composite Service.
 *
 * @author psh111
 */
@RestController
public class HealthController extends BaseController {

	/**
	 * Health check.
	 *
	 * @param request ServletRequest to be passed as parameter
	 * @return the mono as HttpStatus of the service.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/health")
	public ResponseEntity<Void> healthCheck(HttpServletRequest request) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
