package com.prueba.literalura.service;

import com.prueba.literalura.model.Idioma;
import com.prueba.literalura.model.Libro;
import com.prueba.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository repository;


    public Libro obtenerLibroPorNombre(String titulo) {
        return repository.findByTitulo(titulo);
    }

    public void guardarLibro(Libro libro) {
        repository.save(libro);
    }

    public List<Libro> obtenerLibros() {
        return repository.findAll();
    }

    public List<Libro> obtenerLibrosPorIdioma(String idioma) {
        Idioma idiomaLibro = Idioma.fromString(idioma);
        return repository.findByIdiomas(idiomaLibro);
    }
}
