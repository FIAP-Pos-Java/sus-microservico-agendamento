package sus.microservico.agendamento.sus_microservico_agendamento.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sus.microservico.agendamento.sus_microservico_agendamento.controller.dto.BuscarCirurgiaDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.controller.dto.CirurgiaDTO;
import sus.microservico.agendamento.sus_microservico_agendamento.service.CirurgiaService;

@RestController
@RequestMapping("api/v1/cirurgias")
@RequiredArgsConstructor
public class CirurgiaController {

    private final Logger logger = LoggerFactory.getLogger(CirurgiaController.class);
    private final CirurgiaService cirurgiaService;

    @GetMapping
    public ResponseEntity<Page<BuscarCirurgiaDTO>> buscarTodasCirurgias(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        this.logger.info("GET -> /api/v1/cirurgias?page={}&size={}", page, size);
        Page<BuscarCirurgiaDTO> cirurgias = this.cirurgiaService.buscarTodasCirurgias(page, size);
        return ResponseEntity.ok(cirurgias);
    }

    @GetMapping("{id}")
    public ResponseEntity<BuscarCirurgiaDTO> buscarCirurgiaPorId(@PathVariable String id) {
        this.logger.info("GET -> /api/v1/cirurgias/{}", id);
        BuscarCirurgiaDTO cirurgia = this.cirurgiaService.buscarCirurgiaPorId(id);
        return ResponseEntity.ok(cirurgia);
    }

    @PostMapping
    public ResponseEntity<Void> agendarCirurgia(@Valid @RequestBody CirurgiaDTO dto) {
        this.logger.info("POST -> /api/v1/cirurgias");
        this.cirurgiaService.agendarCirurgia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarCirurgia(
            @PathVariable String id,
            @Valid @RequestBody CirurgiaDTO dto
    ) {
        this.logger.info("PUT -> /api/v1/cirurgias/{}", id);
        this.cirurgiaService.atualizarCirurgia(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> cancelarCirurgia(@PathVariable String id) {
        this.logger.info("DELETE -> /api/v1/cirurgias/{}", id);
        this.cirurgiaService.cancelarCirurgia(id);
        return ResponseEntity.noContent().build();
    }
}
