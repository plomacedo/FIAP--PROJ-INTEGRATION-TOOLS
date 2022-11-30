package br.fiap.integrations.droneproducer.controller;

import br.fiap.integrations.droneproducer.entities.TimeDetails;
import br.fiap.integrations.droneproducer.sender.QueueSender;
import br.fiap.integrations.droneproducer.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/controller")
public class JobController {

    private final PlayerService service;

    public JobController(PlayerService service) {
        this.service = service;
    }

    @PostMapping("/main")
    public void runMain(){
        service.runTimer();
    }

    @GetMapping
    public List<TimeDetails> getAllRunningTimers(){
        return service.getAllRunningTimers();
    }

    @GetMapping("/{timerId}")
    public TimeDetails getRunningTimer(@PathVariable String timerId){
        return service.getRunningTimer(timerId);
    }

    @DeleteMapping("/{timerId}")
    public Boolean deleteTimer(@PathVariable String timerId){
        return service.deleteTimer(timerId);
    }

    @Autowired
    private QueueSender sender;

}