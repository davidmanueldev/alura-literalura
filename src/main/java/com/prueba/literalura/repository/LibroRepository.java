package com.prueba.literalura.repository;

import com.prueba.literalura.model.Idioma;
import com.prueba.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Libro findByTitulo(String titulo);

    List<Libro> findByIdiomas(Idioma idioma);

    // prueba 1/1
}
