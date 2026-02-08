package sus.microservico.agendamento.sus_microservico_agendamento.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sus.microservico.agendamento.sus_microservico_agendamento.config.RabbitMQConfig;
import sus.microservico.agendamento.sus_microservico_agendamento.controller.dto.BuscarCirurgiaDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.controller.dto.CirurgiaDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.event.CirurgiaAgendadaEvent;
import sus.microservico.agendamento.sus_microservico_agendamento.model.Cirurgia;
import sus.microservico.agendamento.sus_microservico_agendamento.model.enums.StatusCirurgia;
import sus.microservico.agendamento.sus_microservico_agendamento.repository.CirurgiaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CirurgiaService {

    private final Logger logger = LoggerFactory.getLogger(CirurgiaService.class);
    private final CirurgiaRepository cirurgiaRepository;
    private final RabbitTemplate rabbitTemplate;

    public Page<BuscarCirurgiaDTO> buscarTodasCirurgias(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataAgendamento").descending());
        Page<Cirurgia> cirurgias = this.cirurgiaRepository.findAll(pageable);
        return cirurgias.map(cirurgia -> new BuscarCirurgiaDTO(
                cirurgia.getId(),
                cirurgia.getPacienteId(),
                cirurgia.getMedicoId(),
                cirurgia.getDataCirurgia(),
                cirurgia.getHoraCirurgia(),
                cirurgia.getLocal(),
                cirurgia.getDescricao(),
                cirurgia.getStatus(),
                cirurgia.getDataAgendamento()
        ));
    }

    public BuscarCirurgiaDTO buscarCirurgiaPorId(String id) {
        UUID uuid = UUID.fromString(id);
        Cirurgia cirurgia = this.cirurgiaRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Cirurgia não encontrada"));
        return new BuscarCirurgiaDTO(
                cirurgia.getId(),
                cirurgia.getPacienteId(),
                cirurgia.getMedicoId(),
                cirurgia.getDataCirurgia(),
                cirurgia.getHoraCirurgia(),
                cirurgia.getLocal(),
                cirurgia.getDescricao(),
                cirurgia.getStatus(),
                cirurgia.getDataAgendamento()
        );
    }

    public void agendarCirurgia(CirurgiaDTO dto) {
        // Criar e salvar cirurgia
        Cirurgia novaCirurgia = new Cirurgia();
        novaCirurgia.setPacienteId(dto.pacienteId());
        novaCirurgia.setMedicoId(dto.medicoId());
        novaCirurgia.setDataCirurgia(dto.dataCirurgia());
        novaCirurgia.setHoraCirurgia(dto.horaCirurgia());
        novaCirurgia.setLocal(dto.local());
        novaCirurgia.setDescricao(dto.descricao());
        novaCirurgia.setStatus(StatusCirurgia.AGENDADA);
        novaCirurgia.setDataAgendamento(LocalDateTime.now());
        
        Cirurgia cirurgiaSalva = this.cirurgiaRepository.save(novaCirurgia);
        this.logger.info("Cirurgia {} agendada para {} às {}", cirurgiaSalva.getId(), dto.dataCirurgia(), dto.horaCirurgia());

        // Publicar evento no RabbitMQ
        CirurgiaAgendadaEvent evento = new CirurgiaAgendadaEvent(
                cirurgiaSalva.getId(),
                cirurgiaSalva.getPacienteId(),
                cirurgiaSalva.getMedicoId(),
                cirurgiaSalva.getDataCirurgia(),
                cirurgiaSalva.getHoraCirurgia(),
                cirurgiaSalva.getLocal()
        );
        
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.CIRURGIA_AGENDADA_ROUTING_KEY,
                evento
        );
        this.logger.info("Evento CirurgiaAgendadaEvent publicado no RabbitMQ para cirurgia {}", cirurgiaSalva.getId());
    }

    public void atualizarCirurgia(String id, CirurgiaDTO dto) {
        UUID uuid = UUID.fromString(id);
        Cirurgia cirurgia = this.cirurgiaRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Cirurgia não encontrada"));
        
        cirurgia.setDataCirurgia(dto.dataCirurgia());
        cirurgia.setHoraCirurgia(dto.horaCirurgia());
        cirurgia.setLocal(dto.local());
        cirurgia.setDescricao(dto.descricao());
        
        this.cirurgiaRepository.save(cirurgia);
        this.logger.info("Cirurgia {} atualizada", id);
    }

    public void cancelarCirurgia(String id) {
        UUID uuid = UUID.fromString(id);
        Cirurgia cirurgia = this.cirurgiaRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Cirurgia não encontrada"));
        
        cirurgia.setStatus(StatusCirurgia.CANCELADA);
        this.cirurgiaRepository.save(cirurgia);
        this.logger.info("Cirurgia {} cancelada", id);
    }
}
