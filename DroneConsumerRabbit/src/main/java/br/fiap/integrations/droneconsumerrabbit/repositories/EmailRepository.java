package br.fiap.integrations.droneconsumerrabbit.repositories;


import br.fiap.integrations.droneconsumerrabbit.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
}
