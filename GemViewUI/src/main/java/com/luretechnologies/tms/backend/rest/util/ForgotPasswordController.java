/**
 * 
 */
package com.luretechnologies.tms.backend.rest.util;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vinay
 *
 */
@RestController
@RequestMapping("recovery")
public class ForgotPasswordController {
	

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
	public String forgotPassword() {
		return "passwordReset";
	}

}
