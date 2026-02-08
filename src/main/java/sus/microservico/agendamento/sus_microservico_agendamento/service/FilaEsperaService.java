package sus.microservico.agendamento.sus_microservico_agendamento.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sus.microservico.agendamento.sus_microservico_agendamento.controller.dto.BuscarFilaEsperaDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.controller.dto.FilaEsperaDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.model.FilaEspera;
import sus.microservico.agendamento.sus_microservico_agendamento.model.enums.StatusFila;
import sus.microservico.agendamento.sus_microservico_agendamento.repository.FilaEsperaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilaEsperaService {

    private final Logger logger = LoggerFactory.getLogger(FilaEsperaService.class);
    private final FilaEsperaRepository filaEsperaRepository;

    public Page<BuscarFilaEsperaDTO> buscarTodasFilas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataInclusao").ascending());
        Page<FilaEspera> filas = this.filaEsperaRepository.findAll(pageable);
        return filas.map(fila -> new BuscarFilaEsperaDTO(
                fila.getId(),
                fila.getPacienteId(),
                fila.getDataInclusao(),
                fila.getStatus(),
                fila.getPosicao()
        ));
    }

    public BuscarFilaEsperaDTO buscarFilaPorId(String id) {
        UUID uuid = UUID.fromString(id);
        FilaEspera fila = this.filaEsperaRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Fila não encontrada"));
        return new BuscarFilaEsperaDTO(
                fila.getId(),
                fila.getPacienteId(),
                fila.getDataInclusao(),
                fila.getStatus(),
                fila.getPosicao()
        );
    }

    public void adicionarNaFila(FilaEsperaDTO dto) {
        Long posicaoAtual = this.filaEsperaRepository.countByStatus(StatusFila.AGUARDANDO);
        
        FilaEspera novaFila = new FilaEspera();
        novaFila.setPacienteId(dto.pacienteId());
        novaFila.setDataInclusao(LocalDateTime.now());
        novaFila.setStatus(StatusFila.AGUARDANDO);
        novaFila.setPosicao(posicaoAtual.intValue() + 1);
        
        this.filaEsperaRepository.save(novaFila);
        this.logger.info("Paciente {} adicionado na fila na posição {}", dto.pacienteId(), novaFila.getPosicao());
    }

    public void removerDaFila(String id) {
        UUID uuid = UUID.fromString(id);
        FilaEspera fila = this.filaEsperaRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Fila não encontrada"));
        this.filaEsperaRepository.deleteById(uuid);
        this.logger.info("Fila {} removida", id);
    }

    public void atualizarStatusFila(UUID filaId, StatusFila novoStatus) {
        FilaEspera fila = this.filaEsperaRepository.findById(filaId)
                .orElseThrow(() -> new RuntimeException("Fila não encontrada"));
        fila.setStatus(novoStatus);
        this.filaEsperaRepository.save(fila);
        this.logger.info("Status da fila {} atualizado para {}", filaId, novoStatus);
    }
}
