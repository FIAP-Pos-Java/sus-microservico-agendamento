package sus.microservico.agendamento.sus_microservico_agendamento.client;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sus.microservico.agendamento.sus_microservico_agendamento.client.dto.BuscarMedicoDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.client.dto.BuscarPacienteDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoreServiceClient {
    
    private final Logger logger = LoggerFactory.getLogger(CoreServiceClient.class);
    private final RestTemplate restTemplate;
    private static final String CORE_URL = "http://localhost:8080/api/v1";

    public boolean pacienteExiste(UUID id) {
        try {
            String url = CORE_URL + "/pacientes/" + id;
            ResponseEntity<BuscarPacienteDTO> response = 
                restTemplate.getForEntity(url, BuscarPacienteDTO.class);
            boolean existe = response.getStatusCode().is2xxSuccessful();
            this.logger.info("Validação Paciente {}: {}", id, existe ? "Existe" : "Não existe");
            return existe;
        } catch (Exception e) {
            this.logger.error("Erro ao validar paciente {}: {}", id, e.getMessage());
            return false;
        }
    }

    public boolean medicoExiste(UUID id) {
        try {
            String url = CORE_URL + "/medicos/" + id;
            ResponseEntity<BuscarMedicoDTO> response = 
                restTemplate.getForEntity(url, BuscarMedicoDTO.class);
            boolean existe = response.getStatusCode().is2xxSuccessful();
            this.logger.info("Validação Médico {}: {}", id, existe ? "Existe" : "Não existe");
            return existe;
        } catch (Exception e) {
            this.logger.error("Erro ao validar médico {}: {}", id, e.getMessage());
            return false;
        }
    }

    public BuscarPacienteDTO buscarPaciente(UUID id) {
        try {
            String url = CORE_URL + "/pacientes/" + id;
            ResponseEntity<BuscarPacienteDTO> response = 
                restTemplate.getForEntity(url, BuscarPacienteDTO.class);
            return response.getBody();
        } catch (Exception e) {
            this.logger.error("Erro ao buscar paciente {}: {}", id, e.getMessage());
            return null;
        }
    }

    public BuscarMedicoDTO buscarMedico(UUID id) {
        try {
            String url = CORE_URL + "/medicos/" + id;
            ResponseEntity<BuscarMedicoDTO> response = 
                restTemplate.getForEntity(url, BuscarMedicoDTO.class);
            return response.getBody();
        } catch (Exception e) {
            this.logger.error("Erro ao buscar médico {}: {}", id, e.getMessage());
            return null;
        }
    }
}
