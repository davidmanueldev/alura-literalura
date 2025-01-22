package com.prueba.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor", // Nombre de la tabla de unión en la base de datos
            joinColumns = @JoinColumn(name = "libro_id"), // Clave foránea hacia los libros
            inverseJoinColumns = @JoinColumn(name = "autor_id") // Clave foránea hacia los autores
    )
    private List<Autor> autores = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Idioma idiomas;

    private Double numDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idiomas = Idioma.fromString(datosLibro.idiomas().get(0));
        this.numDescargas = datosLibro.numDescargas();
        this.autores = new ArrayList<>();
    }

    // Relacionar libro con autor (agregar autor a la lista de autores del libro)
    public void agregarAutor(Autor autor) {
        if (!autores.contains(autor)) {
            autores.add(autor);
        }
        if (!autor.getLibros().contains(this)) {  // Relacionar autor con libro (agregar libro a la lista de libros del autor)
            autor.agregarLibro(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idiomas = idiomas;
    }

    public Idioma getIdiomas() {
        return idiomas;
    }

    public Double getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(Double numDescargas) {
        this.numDescargas = numDescargas;
    }

    @Override
    public String toString() {
        return "-----------Libro--------------\n" +
                "Titulo = '" + titulo + "\n" +
                "Autores = " + autores.stream().map(Autor::getNombre).toList() + "\n" +
                "Idiomas = " + idiomas + "\n" +
                "numeroDescargas = " + numDescargas + "\n" +
                "------------------------------";
    }
}
