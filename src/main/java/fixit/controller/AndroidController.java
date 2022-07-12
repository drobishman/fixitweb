package fixit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fixit.model.User;
import fixit.service.UserService;
import fixit.util.Status;

import javax.validation.Valid;
import java.util.List;
 
import org.json.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping(value="/android")
public class AndroidController {
	
	@Autowired
    UserService userService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String jsonObject(String ssoId, String password){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		System.out.println("ssoId: " + ssoId + " password: " + password);
		
		User user = userService.findBySSO(ssoId);
        
		if (encoder.matches(password, user.getPassword())) {
			
			JSONObject jsonObject = new JSONObject(user);
			return jsonObject.toString();
		}
			
		else
			return "fail";
	}
	
   

}