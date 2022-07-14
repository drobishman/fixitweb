package fixit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fixit.model.TroubleCode;
import fixit.model.User;
import fixit.model.UserProfile;
import fixit.service.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.json.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping(value="/android")
public class AndroidController {

	@Autowired
	UserService userService;

	@Autowired
	TroubleCodeService troubleCodeService;

	@Autowired
	UserProfileService userProfileService;

	/**
	 * 
	 * Method that returns an user as JSONObject from credentials
	 * 
	 * @param ssoId
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String androidLogin(String ssoId, String password){

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		User user = userService.findBySSO(ssoId);


		if(Objects.isNull(user))
			return "FAIL";

		if (encoder.matches(password, user.getPassword()) && user.isLoggedIn() != true) {

			user.setLoggedIn(true);
			JSONObject jsonObject = new JSONObject(user);
			return jsonObject.toString();
		}

		else
			return "FAIL";
	}

	@RequestMapping(value="/register", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String androidRegister(String firstName, String lastName, String ssoId, String password, String email){

		User newUser = new User ();

		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setSsoId(ssoId);
		newUser.setPassword(password);
		newUser.setEmail(email);

		UserProfile userProfile= new UserProfile ();
		userProfile.setId(1);
		userProfile.setType("USER");

		Set<UserProfile> userProfiles = new HashSet<UserProfile>();;
		userProfiles.add(userProfile);

		newUser.setUserProfiles(userProfiles);

		System.out.println(newUser.toString());

		try {
			userService.saveUser(newUser);
		} catch (Exception e) {
			return "FAIL";
		}

		return "SUCCESS";

	}


	/**
	 * Find a trouble code by ID
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/troublecodebyid-{troubleCode.id}", method=RequestMethod.GET, produces = "application/json")
	public  @ResponseBody String troubleCode(@PathVariable("troubleCode.id") int id){

		TroubleCode troubleCode = this.troubleCodeService.findById(id);

		if(troubleCode== null) return "NOT FOUND";

		JSONObject jsonObject = new JSONObject(troubleCode);
		return jsonObject.toString();
	}


	/**
	 * find a trouble code by number
	 * @param number
	 * @return
	 */
	@RequestMapping(value="/troublecodebynumber-{troubleCode.number}", method=RequestMethod.GET, produces = "application/json")
	public  @ResponseBody String troubleCodeNumber(@PathVariable("troubleCode.number") String number){

		TroubleCode troubleCode = this.troubleCodeService.findByNumber(number);

		if(troubleCode== null) return "NOT FOUND";

		JSONObject jsonObject = new JSONObject(troubleCode);
		return jsonObject.toString();
	}

}