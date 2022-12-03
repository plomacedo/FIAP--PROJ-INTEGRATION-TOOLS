package br.fiap.integrations.droneconsumerrabbit.repositories;

import br.fiap.integrations.droneconsumerrabbit.models.DroneRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DroneRiskRepository  extends JpaRepository<DroneRisk, UUID> {
}
