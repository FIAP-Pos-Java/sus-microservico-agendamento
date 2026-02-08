package sus.microservico.agendamento.sus_microservico_agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sus.microservico.agendamento.sus_microservico_agendamento.model.Cirurgia;

import java.util.UUID;

@Repository
public interface CirurgiaRepository extends JpaRepository<Cirurgia, UUID> {
}
