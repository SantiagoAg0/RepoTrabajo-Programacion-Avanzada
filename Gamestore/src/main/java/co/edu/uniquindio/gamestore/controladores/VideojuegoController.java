package co.edu.uniquindio.gamestore.controladores;

import co.edu.uniquindio.gamestore.entidades.Videojuego;
import co.edu.uniquindio.gamestore.enums.Genero;
import co.edu.uniquindio.gamestore.servicios.VideojuegoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    // GET /api/videojuegos/
    @GetMapping("/")
    public ResponseEntity<List<Videojuego>> listarTodos() {
        return ResponseEntity.ok(videojuegoService.listarTodos());
    }

    // GET /api/videojuegos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(videojuegoService.obtenerPorId(id));
    }

    // POST /api/videojuegos/
    @PostMapping("/")
    public ResponseEntity<Videojuego> crear(@Valid @RequestBody Videojuego videojuego) {
        Videojuego creado = videojuegoService.crear(videojuego);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/videojuegos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> actualizar(@PathVariable Long id,
                                                  @Valid @RequestBody Videojuego videojuego) {
        return ResponseEntity.ok(videojuegoService.actualizar(id, videojuego));
    }

    // DELETE /api/videojuegos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        videojuegoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/videojuegos/buscar?titulo={texto}
    @GetMapping("/buscar")
    public ResponseEntity<List<Videojuego>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(videojuegoService.buscarPorTitulo(titulo));
    }

    // GET /api/videojuegos/rango-precio?min={min}&max={max}
    @GetMapping("/rango-precio")
    public ResponseEntity<List<Videojuego>> buscarPorRangoPrecio(@RequestParam Double min,
                                                                   @RequestParam Double max) {
        return ResponseEntity.ok(videojuegoService.buscarPorRangoPrecio(min, max));
    }

    // PATCH /api/videojuegos/{id}/descuento?porcentaje={valor}
    @PatchMapping("/{id}/descuento")
    public ResponseEntity<Videojuego> aplicarDescuento(@PathVariable Long id,
                                                        @RequestParam Double porcentaje) {
        return ResponseEntity.ok(videojuegoService.aplicarDescuento(id, porcentaje));
    }
}
