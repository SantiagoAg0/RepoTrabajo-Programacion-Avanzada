package co.edu.uniquindio.gamestore.entidades;

import co.edu.uniquindio.gamestore.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "videojuegos")
public class Videojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotNull(message = "El precio es obligatorio")
    @Column(nullable = false)
    private Double precio;

    @Column(unique = true, nullable = false)
    private String codigoRegistro;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "desarrolladora_id")
    private Desarrolladora desarrolladora;

    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    @Transient
    private Double precioConIva;

    // -------- Lifecycle Callbacks --------
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    // -------- Constructors --------
    public Videojuego() {}

    // -------- Getters & Setters --------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getCodigoRegistro() { return codigoRegistro; }
    public void setCodigoRegistro(String codigoRegistro) { this.codigoRegistro = codigoRegistro; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public Desarrolladora getDesarrolladora() { return desarrolladora; }
    public void setDesarrolladora(Desarrolladora desarrolladora) { this.desarrolladora = desarrolladora; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public Double getPrecioConIva() { return precioConIva; }
    public void setPrecioConIva(Double precioConIva) { this.precioConIva = precioConIva; }
}
