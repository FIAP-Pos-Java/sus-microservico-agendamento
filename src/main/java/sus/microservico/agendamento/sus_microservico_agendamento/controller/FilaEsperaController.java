package sus.microservico.agendamento.sus_microservico_agendamento.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sus.microservico.agendamento.sus_microservico_agendamento.controller.dto.BuscarFilaEsperaDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.controller.dto.FilaEsperaDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.service.FilaEsperaService;

@RestController
@RequestMapping("api/v1/fila-espera")
@RequiredArgsConstructor
public class FilaEsperaController {

    private final Logger logger = LoggerFactory.getLogger(FilaEsperaController.class);
    private final FilaEsperaService filaEsperaService;

    @GetMapping
    public ResponseEntity<Page<BuscarFilaEsperaDTO>> buscarTodasFilas(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        this.logger.info("GET -> /api/v1/fila-espera?page={}&size={}", page, size);
        Page<BuscarFilaEsperaDTO> filas = this.filaEsperaService.buscarTodasFilas(page, size);
        return ResponseEntity.ok(filas);
    }

    @GetMapping("{id}")
    public ResponseEntity<BuscarFilaEsperaDTO> buscarFilaPorId(@PathVariable String id) {
        this.logger.info("GET -> /api/v1/fila-espera/{}", id);
        BuscarFilaEsperaDTO fila = this.filaEsperaService.buscarFilaPorId(id);
        return ResponseEntity.ok(fila);
    }

    @PostMapping
    public ResponseEntity<Void> adicionarNaFila(@Valid @RequestBody FilaEsperaDTO dto) {
        this.logger.info("POST -> /api/v1/fila-espera");
        this.filaEsperaService.adicionarNaFila(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerDaFila(@PathVariable String id) {
        this.logger.info("DELETE -> /api/v1/fila-espera/{}", id);
        this.filaEsperaService.removerDaFila(id);
        return ResponseEntity.noContent().build();
    }
}
