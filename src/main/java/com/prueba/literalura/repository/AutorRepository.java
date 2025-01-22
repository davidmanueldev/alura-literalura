package com.prueba.literalura.repository;

import com.prueba.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // findByNombre es un metodo que se añade por deafult por la Interface JpaRepository
    // el resto de metodos están añadidos por default por la Interface JpaRepository y se pueden usar sin necesidad de implementarlos
    Autor findByNombre(String nombre);

    //Fallecieron después del año especificado, o No tienen fecha de fallecimiento registrada (aún vivos, en el año especificado).
    // Esto es necesario para que no ocurra lo de la fecha de fallecimiento nula XD
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento > :anio OR a.fechaFallecimiento IS NULL)")
    List<Autor> autoresVivosEnDeterminadoAnio(int anio);

}
