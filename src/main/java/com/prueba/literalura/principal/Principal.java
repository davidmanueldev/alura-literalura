package com.prueba.literalura.principal;

import com.prueba.literalura.model.*;
import com.prueba.literalura.service.AutorService;
import com.prueba.literalura.service.ConsumoAPI;
import com.prueba.literalura.service.ConvierteDatos;
import com.prueba.literalura.service.LibroService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Principal {


    private Scanner leer = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private static LibroService libroService;
    private static AutorService autorService;

    public Principal(LibroService libroService, AutorService autorService) {
        Principal.libroService = libroService;
        Principal.autorService = autorService;
    }

    public void menu() {
        var opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                      ***MENU***
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    
                    0- Salir
                    ******************************
                    Seleccione una opción:
                    """);
            opcion = leer.nextInt();
            leer.nextLine();

            switch (opcion) {
                case 1:
                    obtenerLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opcion no valida...");
            }

        }

    }


    private void obtenerLibroPorTitulo() {
        System.out.println("Titulo del libro a buscar: ");
        var nombreLibro = leer.nextLine();

        // Obtener datos de la API de libros
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + normalizarTextoParaURL(nombreLibro));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        // Buscar libro en los resultados de la API de libros
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> normalizarTexto(l.titulo()).contains(normalizarTexto(nombreLibro)))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado...");
            manejarLibro(libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado...");
        }
    }

    // Normalizar texto para búsquedas en la API de libros
    private String normalizarTexto(String texto) {
        return texto.toUpperCase().trim();
    }

    // Convertir espacios en URL-friendly para búsquedas en la API de libros (reemplazar espacios por +)
    private String normalizarTextoParaURL(String texto) {
        return texto.replace(" ", "+");
    }

    // Manejar lógica de creación/actualización del libro
    private void manejarLibro(DatosLibro datosLibro) {
        Libro libroExistente = libroService.obtenerLibroPorNombre(datosLibro.titulo());
        if (libroExistente != null) {
            System.out.println("El libro ya existe en el sistema");
            System.out.println(libroExistente);
            return;
        }

        // Crear libro si no existe
        Libro libro = new Libro(datosLibro);
        datosLibro.autor().forEach(datosAutor -> libro.agregarAutor(obtenerOGuardarAutor(datosAutor)));
        libroService.guardarLibro(libro);

        System.out.println("Libro guardado correctamente en la Base de Datos");
        System.out.println(libro);
    }

    // Obtener o guardar autor
    private Autor obtenerOGuardarAutor(DatosAutor datosAutor) {
        Autor autor = autorService.obtenerAutorPorNombre(datosAutor.nombre());
        if (autor != null) {
            System.out.println("El autor ya existe en el sistema");
            return autor;
        }

        // Crear nuevo autor
        autor = new Autor(datosAutor);
        autorService.guardarAutor(autor);
        return autor;
    }

    //listar los libros registrados en la Base de Datos
    public void listarLibrosRegistrados() {
        List<Libro> libros = libroService.obtenerLibros();
        libros.forEach(System.out::println);
    }

    //listar los autores registrados en la Base de Datos
    private void listarAutoresRegistrados() {
        List<Autor> autores = autorService.obtenerAutores();
        autores.forEach(System.out::println);
    }

    //listar los autores vivos en X año
    private void listarAutoresVivos() {
        System.out.println("Ingrese el año para listar los autores que estén vivos: ");
        var anio = leer.nextInt();
        leer.nextLine();

        List<Autor> autoresVivos = autorService.obtenerAutoresVivosEnDeterminadoAnio(anio);
        autoresVivos.forEach(System.out::println);
    }

    // Listar libros de acuerdo al idioma
    private void listarLibrosPorIdioma() {
        System.out.println("""
                Seleccione el idioma:
                1- Español
                2- Inglés
                3- Portugués
                """);
        var opc = leer.nextInt();
        leer.nextLine();
        String idioma = "";

        switch (opc) {
            case 1:
                idioma = "es";
                break;
            case 2:
                idioma = "en";
                break;
            case 3:
                idioma = "pt";
                break;
            default:
                System.out.println("Opción no válida >:(");
        }
        List<Libro> librosIdioma = libroService.obtenerLibrosPorIdioma(idioma);
        librosIdioma.forEach(System.out::println);
    }

}
