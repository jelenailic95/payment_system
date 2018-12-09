//package com.sep.proxy.proxy;
//
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//import java.util.Map;
//
//@RestController
//public class Controller {
//
//    @GetMapping("/home")
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> howdy(Model model, Principal principal) {
//        OAuth2Authentication authentication = (OAuth2Authentication) principal;
//        Map<String, Object> user = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
//        model.addAttribute("user", user);
//        return user;
//    }
//}
