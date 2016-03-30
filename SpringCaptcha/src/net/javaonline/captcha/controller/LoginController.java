package net.javaonline.captcha.controller;
import javax.servlet.http.HttpSession;
import net.javaonline.login.form.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	

@RequestMapping(value="/loadLoginPg")
	public String homepage(Model model){
		Login login= new Login();
		model.addAttribute("login", login);
		return "login";
}


@RequestMapping(value = "/login", method = RequestMethod.POST)
public String login(@ModelAttribute("login") Login login,BindingResult result,Model model,HttpSession session) {
	
	if (login.getUserId()==null || login.getUserId().equals(""))
		{
		  	login.setCaptcha("");
	    	model.addAttribute("message", "User Id is required");
	    	return "login";
		}
	

	if (login.getPassword()==null || login.getPassword().equals(""))
	{
	  	login.setCaptcha("");
    	model.addAttribute("message", "Password is required");
    	return "login";
	}	
	
	 String captcha=(String)session.getAttribute("CAPTCHA");
	    if(captcha==null || (captcha!=null && !captcha.equals(login.getCaptcha()))){
	    	login.setCaptcha("");
	    	model.addAttribute("message", "Captcha does not match");
	    	return "login";
	    }

		if(login.getUserId().equals("guest") && login.getPassword().equals("ddd")){
			System.out.println("user id and password matches");
			model.addAttribute("loginId", login.getUserId());
			return "home";
		
		}
		else{
			login.setCaptcha("");
			model.addAttribute("message","User ID or Password Incorrect");
			return "login";	
		}
	
	
}



}
