package fixit.controller;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fixit.model.Car;
import fixit.model.CarTroubleCode;
import fixit.model.TroubleCode;
import fixit.model.User;
import fixit.model.UserProfile;
import fixit.service.CarService;
import fixit.service.TroubleCodeService;
import fixit.service.UserProfileService;
import fixit.service.UserService;
import fixit.util.Status;
import fixit.service.CarTroubleCodeService;
 
 
 
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {
 
    @Autowired
    UserService userService;
     
    @Autowired
    CarService carService;
 
    @Autowired
    TroubleCodeService troubleCodeService;
    
    @Autowired
    CarTroubleCodeService carTroubleCodeService;
 
    @Autowired
    UserProfileService userProfileService;
     
    @Autowired
    MessageSource messageSource;
 
    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
     
    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;
    
    static final Logger logger = LoggerFactory.getLogger(AppController.class);
     
     
    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
 
    	//list all users
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        //list logged user
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        //List all cars
        List<Car> cars = carService.findAllCars();
        model.addAttribute("cars", cars);
        //List all trouble codes
        List<TroubleCode> troubleCodes = troubleCodeService.findAllTroubleCodes();
        model.addAttribute("troubleCodes", troubleCodes);
        //List all car trouble codes
        List<CarTroubleCode> carTroubleCodes = carTroubleCodeService.findAllCarTroubleCodes();
        model.addAttribute("carTroubleCodes", carTroubleCodes);
        return "userslist";
    }
 
    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        return "registration";
    }
 
    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
            ModelMap model) {
 
        if (result.hasErrors()) {
        	
        	System.out.println("Fail 1 " + user.toString() + result.toString());
            return "registration";
        }
        
        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [User].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }
         
        userService.saveUser(user);
 
        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        return "registrationsuccess";
    }
    
    /**
     * This method will provide the medium to add a new trouble code.
     */
    @RequestMapping(value = { "/newTroubleCode" }, method = RequestMethod.GET)
    public String newTroubleCode(ModelMap model) {
        TroubleCode troubleCode = new TroubleCode();
        model.addAttribute("troubleCode", troubleCode);
        model.addAttribute("edit", false);
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        return "addtroublecode";
    }
 
    /**
     * This method will be called on form submission, handling POST request for
     * saving trouble code in database. It also validates the trouble code input
     */
    @RequestMapping(value = { "/newTroubleCode" }, method = RequestMethod.POST)
    public String saveTroubleCode(@Valid TroubleCode troubleCode, BindingResult result,
            ModelMap model) {
 
        if (result.hasErrors()) {
            return "addtroublecode";
        }
         
        troubleCodeService.saveTroubleCode(troubleCode);
 
        model.addAttribute("success", "TroubleCode " + troubleCode.getNumber() + " "+ troubleCode.getFaultLocation() + " registered successfully");
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        return "addtroublecodesuccess";
    }
    
    /**
     * This method will provide the medium to add a new car.
     */
    @RequestMapping(value = { "/newcar" }, method = RequestMethod.GET)
    public String newCar(ModelMap model) {
        Car car = new Car();
        model.addAttribute("car", car);
        model.addAttribute("edit", false);
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        logger.info(car.toString());
        return "addcar";
    }
    
    /**
     * This method will be called on form submission, handling POST request for
     * saving car in database. It also validates the car input
     */
    @RequestMapping(value = { "/newcar" }, method = RequestMethod.POST)
    public String saveCar(@Valid Car car, BindingResult result,
            ModelMap model) {
 
        if (result.hasErrors()) {
        	logger.info(result.toString() + car.toString());
            return "addcar";
        }
         
        carService.saveCar(car);
 
        model.addAttribute("success", "car " + car.getRegistrationNumber() + " "+ car.getChasisNumber() +" "+ car.getBrand() +" "+ car.getModel()+ " registered successfully");
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        
        Set <Car> userCars =  loggedinuser.getUserCars();
        userCars.add(car);
        loggedinuser.setUserCars(userCars);
        userService.updateUser(loggedinuser);
        
        logger.info(loggedinuser.getUserCars().toString());
        
        return "addcarsuccess";
    }
 
    
 
 
    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        return "registration";
    }
     
    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
            ModelMap model, @PathVariable String ssoId) {
 
        if (result.hasErrors()) {
            return "registration";
        }
 
        /*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }*/
 
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        
        user.setUserCars(loggedinuser.getUserCars());
        
        userService.updateUser(user);
 
        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
        
        return "registrationsuccess";
    }
    
    /**
     * This method will provide the medium to update an existing car.
     */
    @RequestMapping(value = { "/edit-car-{id}" }, method = RequestMethod.GET)
    public String editCar(@PathVariable int id, ModelMap model) {
        Car car = carService.findById(id);
        model.addAttribute("car", car);
        model.addAttribute("edit", true);
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        return "addcar";
    }
    
    /**
     * This method will be called on form submission, handling POST request for
     * updating car in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-car-{id}" }, method = RequestMethod.POST)
    public String updateCar(@Valid Car car, BindingResult result,
            ModelMap model, @PathVariable int id) {
 
        if (result.hasErrors()) {
            return "addcar";
        }
 
        Car currentCar = carService.findById(id);
        model.addAttribute("currentCar", currentCar);
        
        car.setCarTroubleCodes(currentCar.getCarTroubleCodes());
        
        carService.updateCar(car);
 
        model.addAttribute("success", "User " + car.getBrand() + " "+ car.getModel() + " updated successfully");
        
        return "addcarsuccess";
    }
     
   
    /**
     * This method will provide the medium to update an existing trouble code.
     */
    @RequestMapping(value = { "/edit-trouble_code-{id}" }, method = RequestMethod.GET)
    public String editTroubleCode(@PathVariable int id, ModelMap model) {
        TroubleCode troubleCode = troubleCodeService.findById(id);
        model.addAttribute("troubleCode", troubleCode);
        model.addAttribute("edit", true);
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        return "addtroublecode";
    }
    
    /**
     * This method will be called on form submission, handling POST request for
     * updating trouble code in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-trouble_code-{id}" }, method = RequestMethod.POST)
    public String updateTroubleCode(@Valid TroubleCode troubleCode, BindingResult result,
            ModelMap model, @PathVariable int id) {
 
        if (result.hasErrors()) {
            return "addtroublecode";
        }
 
        TroubleCode currentTroubleCode = troubleCodeService.findById(id);
        model.addAttribute("currentTroubleCode", currentTroubleCode);
        
        troubleCodeService.updateTroubleCode(troubleCode);
 
        model.addAttribute("success", "User " + troubleCode.getNumber() + " "+ troubleCode.getFaultLocation() + " updated successfully");
        
        return "addtroublecodesuccess";
    }
    
    /**
     * This method will provide the medium to update an existing car trouble code.
     */
    @RequestMapping(value = { "/edit-car_trouble_code-{id}" }, method = RequestMethod.GET)
    public String editCarTroubleCode(@PathVariable int id, ModelMap model) {
    	
        CarTroubleCode carTroubleCode = carTroubleCodeService.findById(id);
        
        model.addAttribute("carTroubleCode", carTroubleCode);
        model.addAttribute("edit", true);
        User loggedinuser = userService.findBySSO(getPrincipal());
        model.addAttribute("loggedinuser", loggedinuser);
        return "editcartroublecode";
    }
    
    /**
     * This method will be called on form submission, handling POST request for
     * updating car trouble code in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-car_trouble_code-{id}" }, method = RequestMethod.POST)
    public String updateCarTroubleCode(@Valid CarTroubleCode carTroubleCode, BindingResult result,
            ModelMap model, @PathVariable int id) {
 
        if (result.hasErrors()) {
            return "editcartroublecode";
        }
 
        CarTroubleCode currentCarTroubleCode = carTroubleCodeService.findById(id);
        model.addAttribute("currentCarTroubleCode", currentCarTroubleCode);
        
        carTroubleCodeService.updateCarTroubleCode(carTroubleCode);
        
        System.out.println(carTroubleCode.toString());
 
        model.addAttribute("success", "User " + carTroubleCode.getJob() + " updated successfully");
        
        return "editcartroublecodesuccess";
    }
 
     
    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId) {
        userService.deleteUserBySSO(ssoId);
        return "redirect:/list";
    }
    
    /**
     * This method will delete a car by it's ID value.
     */
    @RequestMapping(value = { "/delete-car-{id}" }, method = RequestMethod.GET)
    public String deleteCar(@PathVariable int id) {
        carService.deleteCarById(id);
        return "redirect:/list";
    }
    
    /**
     * This method will delete an trouble code by it's id value.
     */
    @RequestMapping(value = { "/delete-trouble_code-{id}" }, method = RequestMethod.GET)
    public String deleteTroubleCode(@PathVariable int id) {
        troubleCodeService.deleteTroubleCodeById(id);
        return "redirect:/list";
    }
     
 
    /**
     * This method will provide UserProfile list to views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }
     
    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }
 
    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/list";  
        }
    }
 
    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }
 
    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        
        return userName;
    }
     
    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
}
