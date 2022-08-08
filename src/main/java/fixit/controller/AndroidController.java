package fixit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fixit.model.Car;
import fixit.model.TroubleCode;
import fixit.model.User;
import fixit.model.UserProfile;
import fixit.service.*;

import java.util.HashSet;
import java.util.List;
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

	@Autowired
	CarService carService;

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
		if (encoder.matches(password, user.getPassword())) {
			//deleted checks if user is logged in.
			user.setLoggedIn(true);
			System.out.println("In user logged in = " + user.isLoggedIn());
			userService.updateUser(user);
			JSONObject jsonObject = new JSONObject(user);
			return jsonObject.toString();

		}else return "FAIL";
	}

	/**
	 * Method used to register a new user from android
	 * 
	 * @param firstName
	 * @param lastName
	 * @param ssoId
	 * @param password
	 * @param email
	 * @return
	 */

	@RequestMapping(value="/register", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String androidRegister(String firstName, String lastName, String ssoId, String password, String email){

		User newUser = new User ();

		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setSsoId(ssoId);
		newUser.setPassword(password);
		newUser.setEmail(email);
		newUser.setLoggedIn(true);

		UserProfile userProfile= new UserProfile ();
		userProfile.setId(1);
		userProfile.setType("USER");

		Set<UserProfile> userProfiles = new HashSet<UserProfile>();;
		userProfiles.add(userProfile);

		newUser.setUserProfiles(userProfiles);

		try {
			userService.saveUser(newUser);
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}


		JSONObject jsonObject = new JSONObject(newUser);
		return jsonObject.toString();

	}

	@RequestMapping(value="/update", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String androidUpdate(String firstName, String lastName, String ssoId, String password, String email){

		User newUser = new User ();

		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setSsoId(ssoId);
		newUser.setPassword(password);
		newUser.setEmail(email);
		newUser.setLoggedIn(true);

		UserProfile userProfile= new UserProfile ();
		userProfile.setId(1);
		userProfile.setType("USER");

		Set<UserProfile> userProfiles = new HashSet<UserProfile>();;
		userProfiles.add(userProfile);

		newUser.setUserProfiles(userProfiles);

		newUser.setUserCars(userService.findBySSO(ssoId).getUserCars());

		System.out.println("Android Controller"+newUser.toString());

		try {
			userService.updateUser(newUser);
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}


		JSONObject jsonObject = new JSONObject(newUser);
		return jsonObject.toString();

	}

	/**
	 * 
	 * Method used to logout from android application
	 * 
	 * @param ssoId
	 * @return
	 */

	@RequestMapping(value="/logoff", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String androidLogoff(String ssoId){

		User user = userService.findBySSO(ssoId);
		if(!Objects.isNull(user)) {
			user.setLoggedIn(false);
			userService.updateUser(user);
			return "SUCCESS";
		} else return "FAILURE";
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

	/**
	 * 
	 * method to add a new car to a user
	 * 
	 * @param ssoId
	 * @param registrationNumber
	 * @param chasisNumber
	 * @param brand
	 * @param model
	 * @return
	 */

	@RequestMapping(value="/addcar", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String androidAddCar(String ssoId, String registrationNumber, String chasisNumber, String brand, String model){

		Car newCar = new Car ();

		newCar.setRegistrationNumber(registrationNumber);
		newCar.setChasisNumber(chasisNumber);
		newCar.setBrand(brand);
		newCar.setModel(model);

		User user = userService.findBySSO(ssoId);

		if(!Objects.isNull(user)) {

			carService.saveCar(newCar);
			user.getUserCars().add(newCar);
			userService.updateUser(user);
			return "SUCCESS";

		} else 
			return "FAILURE";
	}


	/**
	 * method used to delete a car by ID.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deletecar", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String androidDeleteCar(int id){


		try {
			carService.deleteCarById(id);
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILURE";
		}

		/**
		 * method used to update a car by ID
		 * @param id
		 * @param registrationNumber
		 * @param chasisNumber
		 * @param brand
		 * @param model
		 * @return
		 */
		@RequestMapping(value="/updatecar", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody String androidUpdateCar(int id, String registrationNumber, String chasisNumber, String brand, String model){

			Car car = carService.findById(id);
			
			car.setChasisNumber(chasisNumber);
			car.setRegistrationNumber(registrationNumber);
			car.setBrand(brand);
			car.setModel(model);

			try {
				carService.updateCar(car);
				return "SUCCESS";
			} catch (Exception e) {
				e.printStackTrace();
				return "FAILURE";
			}
	}


}