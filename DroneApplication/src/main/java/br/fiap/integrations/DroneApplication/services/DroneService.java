package br.fiap.integrations.DroneApplication.services;

import br.fiap.integrations.DroneApplication.entities.Drone;
import br.fiap.integrations.DroneApplication.repositories.DroneRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DroneService {

    final DroneRepository droneRepository;

    public DroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Transactional
    public Drone save(Drone drone){
        return droneRepository.save(drone);
    }

    public List<Drone> findAll(){
        return droneRepository.findAll();
    }

    public Optional<Drone> findById(UUID id){
        return droneRepository.findById(id);
    }

}
