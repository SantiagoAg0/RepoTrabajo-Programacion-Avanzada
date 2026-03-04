package co.edu.uniquindio.gamestore.servicios;

import co.edu.uniquindio.gamestore.entidades.Desarrolladora;
import co.edu.uniquindio.gamestore.entidades.Videojuego;
import co.edu.uniquindio.gamestore.enums.Genero;
import co.edu.uniquindio.gamestore.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.gamestore.repositorios.DesarrolladoraRepository;
import co.edu.uniquindio.gamestore.repositorios.VideojuegoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VideojuegoService {

    private static final double IVA = 1.19;

    private final VideojuegoRepository videojuegoRepository;
    private final DesarrolladoraRepository desarrolladoraRepository;

    public VideojuegoService(VideojuegoRepository videojuegoRepository,
                             DesarrolladoraRepository desarrolladoraRepository) {
        this.videojuegoRepository = videojuegoRepository;
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    // -------- Utilidad: calcular IVA --------
    private void calcularIva(Videojuego v) {
        v.setPrecioConIva(v.getPrecio() * IVA);
    }

    private void calcularIvaLista(List<Videojuego> lista) {
        lista.forEach(this::calcularIva);
    }

    // -------- Validaciones comunes --------
    private void validarVideojuego(Videojuego videojuego) {
        if (videojuego.getTitulo() == null || videojuego.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título del videojuego no puede estar vacío.");
        }
        if (videojuego.getPrecio() == null || videojuego.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (videojuego.getDesarrolladora() == null || videojuego.getDesarrolladora().getId() == null) {
            throw new IllegalArgumentException("Debe asignar una desarrolladora al videojuego.");
        }
        Desarrolladora desarrolladora = desarrolladoraRepository
                .findById(videojuego.getDesarrolladora().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Desarrolladora", videojuego.getDesarrolladora().getId()));
        videojuego.setDesarrolladora(desarrolladora);
    }

    // -------- CRUD --------
    public List<Videojuego> listarTodos() {
        List<Videojuego> lista = videojuegoRepository.findAll();
        calcularIvaLista(lista);
        return lista;
    }

    public Videojuego obtenerPorId(Long id) {
        Videojuego v = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego", id));
        calcularIva(v);
        return v;
    }

    public Videojuego crear(Videojuego videojuego) {
        validarVideojuego(videojuego);
        Videojuego guardado = videojuegoRepository.save(videojuego);
        calcularIva(guardado);
        return guardado;
    }

    public Videojuego actualizar(Long id, Videojuego datosNuevos) {
        Videojuego existente = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego", id));

        existente.setTitulo(datosNuevos.getTitulo());
        existente.setPrecio(datosNuevos.getPrecio());
        existente.setCodigoRegistro(datosNuevos.getCodigoRegistro());
        existente.setGenero(datosNuevos.getGenero());
        existente.setDesarrolladora(datosNuevos.getDesarrolladora());

        validarVideojuego(existente);
        Videojuego actualizado = videojuegoRepository.save(existente);
        calcularIva(actualizado);
        return actualizado;
    }

    public void eliminar(Long id) {
        if (!videojuegoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Videojuego", id);
        }
        videojuegoRepository.deleteById(id);
    }

    // -------- Consultas --------
    public List<Videojuego> buscarPorTitulo(String titulo) {
        List<Videojuego> lista = videojuegoRepository.findByTituloContainingIgnoreCase(titulo);
        calcularIvaLista(lista);
        return lista;
    }

    public List<Videojuego> buscarPorGenero(Genero genero) {
        List<Videojuego> lista = videojuegoRepository.findByGenero(genero);
        calcularIvaLista(lista);
        return lista;
    }

    public List<Videojuego> buscarPorRangoPrecio(Double min, Double max) {
        if (min > max) {
            throw new IllegalArgumentException(
                    "El precio mínimo no puede ser mayor que el precio máximo.");
        }
        List<Videojuego> lista = videojuegoRepository.findByRangoDePrecio(min, max);
        calcularIvaLista(lista);
        return lista;
    }

    public List<Videojuego> obtenerUltimos() {
        List<Videojuego> lista = videojuegoRepository.findUltimosRegistrados();
        calcularIvaLista(lista);
        return lista;
    }

    // -------- Regla de Negocio: Descuento --------
    public Videojuego aplicarDescuento(Long id, Double porcentaje) {
        if (porcentaje == null || porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException(
                    "El porcentaje de descuento debe estar entre 0 y 100.");
        }
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego", id));

        double nuevoPrecio = videojuego.getPrecio() * (1 - porcentaje / 100.0);
        videojuego.setPrecio(nuevoPrecio);
        Videojuego guardado = videojuegoRepository.save(videojuego);
        calcularIva(guardado);
        return guardado;
    }
}
