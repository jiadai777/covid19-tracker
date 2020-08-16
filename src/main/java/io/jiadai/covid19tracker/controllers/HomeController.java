package io.jiadai.covid19tracker.controllers;

import io.jiadai.covid19tracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("stats", service.getAllStats());
        model.addAttribute("totalWorldCases", service.getWorldCasesToday());
        model.addAttribute("totalUsCases", service.getUsCasesToday());
        model.addAttribute("jsDataString", service.getDataString());
        return "home";
    }
}
