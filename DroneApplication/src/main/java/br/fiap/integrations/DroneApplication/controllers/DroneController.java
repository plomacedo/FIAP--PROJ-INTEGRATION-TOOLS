package br.fiap.integrations.DroneApplication.controllers;

import br.fiap.integrations.DroneApplication.entities.Drone;
import br.fiap.integrations.DroneApplication.services.DroneService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;

@Controller
public class DroneController {

    final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/saveDrone", method = RequestMethod.POST)
    public ModelAndView saveDrone(@Valid @ModelAttribute Drone droneDTO){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("drone_information");
        var drone = new Drone();
         BeanUtils.copyProperties(droneDTO, drone);
        modelAndView.addObject("drone", drone);
        droneService.save(drone);
        return modelAndView;
    }

    @RequestMapping(value = "/listDrone", method = RequestMethod.GET)
    public String ListDrone(Model model){
        model.addAttribute("drones",droneService.findAll() );
        return "/listDrone";
    }

    @GetMapping("/externalAccess")
    public ResponseEntity<List<Drone>> getAll() {
        return new ResponseEntity<>(droneService.findAll(), HttpStatus.OK);
    }
}
