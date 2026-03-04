package co.edu.uniquindio.gamestore.repositorios;

import co.edu.uniquindio.gamestore.entidades.Desarrolladora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesarrolladoraRepository extends JpaRepository<Desarrolladora, Long> {
}
