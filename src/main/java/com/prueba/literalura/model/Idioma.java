package com.prueba.literalura.model;

public enum Idioma {
    ESPAÑOL("es"),
    INGLES("en"),
    PORTUGUES("pt");

    private String idioma;

    Idioma(String idioma) {
        this.idioma = idioma;
    }

    public String getIdioma() {
        return idioma;
    }

    public static Idioma fromString(String idioma) {
        for (Idioma idiomaEnum : Idioma.values()) {
            if (idiomaEnum.getIdioma().equals(idioma)) {
                return idiomaEnum;
            }
        }
        return null;
    }


}
