package co.edu.uniquindio.gamestore.repositorios;

import co.edu.uniquindio.gamestore.entidades.Videojuego;
import co.edu.uniquindio.gamestore.enums.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    // Derived Query: buscar por género específico
    List<Videojuego> findByGenero(Genero genero);

    // Derived Query: buscar por título que contenga texto (ignora mayúsculas)
    List<Videojuego> findByTituloContainingIgnoreCase(String titulo);

    // JPQL: buscar por rango de precios
    @Query("SELECT v FROM Videojuego v WHERE v.precio BETWEEN :precioMin AND :precioMax")
    List<Videojuego> findByRangoDePrecio(@Param("precioMin") Double precioMin,
                                         @Param("precioMax") Double precioMax);

    // Consulta nativa: últimos 5 videojuegos por fechaCreacion descendente
    @Query(value = "SELECT * FROM videojuegos ORDER BY fecha_creacion DESC LIMIT 5",
           nativeQuery = true)
    List<Videojuego> findUltimosRegistrados();
}
