package sus.microservico.agendamento.sus_microservico_agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sus.microservico.agendamento.sus_microservico_agendamento.model.FilaEspera;
import sus.microservico.agendamento.sus_microservico_agendamento.model.enums.StatusFila;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilaEsperaRepository extends JpaRepository<FilaEspera, UUID> {
    List<FilaEspera> findByStatusOrderByDataInclusaoAsc(StatusFila status);
    Long countByStatus(StatusFila status);
}
