package co.edu.uniquindio.gamestore.servicios;

import co.edu.uniquindio.gamestore.entidades.Desarrolladora;
import co.edu.uniquindio.gamestore.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.gamestore.repositorios.DesarrolladoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DesarrolladoraService {

    private final DesarrolladoraRepository desarrolladoraRepository;

    public DesarrolladoraService(DesarrolladoraRepository desarrolladoraRepository) {
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    public List<Desarrolladora> listarTodas() {
        return desarrolladoraRepository.findAll();
    }

    public Desarrolladora obtenerPorId(Long id) {
        return desarrolladoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desarrolladora", id));
    }

    public Desarrolladora crear(Desarrolladora desarrolladora) {
        if (desarrolladora.getNombre() == null || desarrolladora.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la desarrolladora es obligatorio.");
        }
        return desarrolladoraRepository.save(desarrolladora);
    }
}
