package fixit.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fixit.model.Car;
import fixit.model.CarTroubleCode;
import fixit.model.TroubleCode;
import fixit.model.User;
import fixit.model.UserProfile;
import fixit.service.*;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import com.google.gson.Gson;

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
	
	@Autowired
	CarTroubleCodeService carTroubleCodeService;

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
			
			
			// to JSONObject
			JSONObject jo = new JSONObject();
			jo.put("id", user.getId());
			jo.put("ssoId", user.getSsoId());
			jo.put("password", user.getPassword());
			jo.put("firstName", user.getFirstName());
			jo.put("lastName", user.getLastName());
			jo.put("email", user.getEmail());
			jo.put("loggedIn", user.isLoggedIn());
			JSONArray ja = new JSONArray();
			for(Car car : user.getUserCars()) {
				JSONObject jobj = new JSONObject();
				jobj.put("id", car.getId());
				jobj.put("registrationNumber", car.getRegistrationNumber());
				jobj.put("chasisNumber", car.getChasisNumber());
				jobj.put("brand", car.getBrand());
				jobj.put("model", car.getModel());
				ja.put(jobj);
			}
			jo.put("userCars", ja);
			
			
			return jo.toString();

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
			return carService.findByChasisNumber(chasisNumber).getId().toString();

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

		List<CarTroubleCode> carTroubleCodes = carTroubleCodeService.findByCarId(id);
		
		for(CarTroubleCode carTroubleCode : carTroubleCodes) {
			carTroubleCodeService.deleteById(carTroubleCode.getId());
		}

		try {
			
			carService.deleteCarById(id);
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILURE";
		}
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
		
		@RequestMapping(value="/addtccar", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody String androidAddTCCar(int id, String number, double lon, double lat){

			Car car = carService.findById(id);
			
			TroubleCode troubleCode = troubleCodeService.findByNumber(number);
			
			if(troubleCode == null) {
				troubleCode = new TroubleCode ();
				troubleCode.setNumber(number);
				troubleCode.setFaultLocation("unknown");
				troubleCodeService.saveTroubleCode(troubleCode);
			}
			
			troubleCode.setId(troubleCodeService.findByNumber(number).getId());
			
			
			CarTroubleCode carTroubleCode = new CarTroubleCode();
			carTroubleCode.setCar(car);
			carTroubleCode.setTroubleCode(troubleCode);
			carTroubleCode.setCarID(car.getId());
			carTroubleCode.setTroubleCodeId(troubleCode.getId());
			carTroubleCode.setLat(lat);
			carTroubleCode.setLon(lon);
			carTroubleCode.setJob("Nothing done yet");
			
			System.out.println(carTroubleCode.toString());
			

			try {
				carTroubleCodeService.saveCarTroubleCode(carTroubleCode);
				return "SUCCESS";
			} catch (Exception e) {
				e.printStackTrace();
				return "FAILURE";
			}
	}
		
		@RequestMapping(value="/viewtccar", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody String androidViewTCCar(int carId){
			
			

			List<CarTroubleCode> carTroubleCodes = carTroubleCodeService.findByCarId(carId);
			JSONObject jo = new JSONObject();
			jo.put("carId", carId);
			jo.put("registrationNumber", carService.findById(carId).getRegistrationNumber());
			
			JSONArray ja = new JSONArray ();
			
			for(CarTroubleCode carTroubleCode : carTroubleCodes) {
				JSONObject jobj = new JSONObject();
				jobj.put("number", carTroubleCode.getTroubleCode().getNumber());
				jobj.put("faultLocation", carTroubleCode.getTroubleCode().getFaultLocation());
				jobj.put("id", carTroubleCode.getTroubleCode().getId());
				jobj.put("job", carTroubleCode.getJob());
				ja.put(jobj);
			}
			jo.put("troubleCodes", ja);
			
			return jo.toString();
			
	}


}