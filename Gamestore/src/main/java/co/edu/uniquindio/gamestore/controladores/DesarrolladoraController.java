package co.edu.uniquindio.gamestore.controladores;

import co.edu.uniquindio.gamestore.entidades.Desarrolladora;
import co.edu.uniquindio.gamestore.servicios.DesarrolladoraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desarrolladoras")
public class DesarrolladoraController {

    private final DesarrolladoraService desarrolladoraService;

    public DesarrolladoraController(DesarrolladoraService desarrolladoraService) {
        this.desarrolladoraService = desarrolladoraService;
    }

    // GET /api/desarrolladoras/
    @GetMapping("/")
    public ResponseEntity<List<Desarrolladora>> listarTodas() {
        return ResponseEntity.ok(desarrolladoraService.listarTodas());
    }

    // POST /api/desarrolladoras/
    @PostMapping("/")
    public ResponseEntity<Desarrolladora> crear(@Valid @RequestBody Desarrolladora desarrolladora) {
        Desarrolladora creada = desarrolladoraService.crear(desarrolladora);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}
