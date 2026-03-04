package co.edu.uniquindio.gamestore.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "desarrolladoras")
public class Desarrolladora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, unique = true)
    private String nombre;

    private String sitioWeb;

    @OneToMany(mappedBy = "desarrolladora", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Videojuego> videojuegos = new ArrayList<>();

    // -------- Constructors --------
    public Desarrolladora() {}

    public Desarrolladora(String nombre, String sitioWeb) {
        this.nombre = nombre;
        this.sitioWeb = sitioWeb;
    }

    // -------- Getters & Setters --------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }

    public List<Videojuego> getVideojuegos() { return videojuegos; }
    public void setVideojuegos(List<Videojuego> videojuegos) { this.videojuegos = videojuegos; }
}
