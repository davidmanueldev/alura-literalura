package com.prueba.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int fechaNacimiento;
    private int fechaFallecimiento;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // Indica que esta es la relación inversa de la relación ManyToMany en la clase Libro (atributo autores)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = Optional.of(datosAutor.fechaNacimiento()).orElse(0);
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
    }

    public void agregarLibro(Libro libro) {

        if (!libros.contains(libro)) {
            libros.add(libro);//Relacionar autor con libro (agregar libro a la lista de libros del autor)
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public int getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(int fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    @Override
    public String toString() {
        //si fechaNacimiento es = 0, mostrar "N/A", sino el valor correspondiente a fechaNacimiento
        String fechaFallecimientoStr = (fechaFallecimiento == 0) ? "N/A" : String.valueOf(fechaFallecimiento);
        return "--------Autor--------\n" +
                " Nombre = " + nombre + "\n" +
                " Año de Nacimiento = " + fechaNacimiento + "\n" +
                " Año de Fallecimiento = " + fechaFallecimientoStr + "\n" +
                " Libros = " + libros.stream().map(Libro::getTitulo).toList() + "\n" +
                "---------------------";
    }
}
