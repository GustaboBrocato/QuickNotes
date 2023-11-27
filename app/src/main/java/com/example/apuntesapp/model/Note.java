package com.example.apuntesapp.model;

/**
 * Clase que representa el modelo de datos de una nota.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La clase se ajusta al patrón MVC al actuar como el modelo (Model)
 *   que encapsula los datos de una nota. Proporciona métodos para acceder y modificar estos datos.
 *
 * - Clean Architecture: Adhiere a los principios de Clean Architecture al centrarse en definir la
 *   estructura y representación de datos de una nota sin lógica de negocio o detalles de implementación.
 *
 * - Model-View-ViewModel (MVVM): Aunque la clase se centra principalmente en el modelo de datos, es
 *   común utilizar modelos en conjunción con ViewModel en el patrón MVVM para gestionar la capa de
 *   presentación y la lógica de presentación de datos en la interfaz de usuario.
 *
 * - Custom Views: Aunque esta clase no es una vista personalizada, forma parte del conjunto de datos
 *   que puede ser utilizado por vistas personalizadas o adaptadores para representar la información
 *   en la interfaz de usuario de manera personalizada.
 */
public class Note {
    private long id;
    private String title;
    private String content;

    /**
     * Constructor por defecto requerido para SQLite.
     */
    public Note() {
        // Constructor por defecto requerido para operaciones con SQLite
    }

    /**
     * Constructor que permite crear una instancia de la clase con datos iniciales.
     *
     * @param title   Título de la nota.
     * @param content Contenido de la nota.
     */
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Métodos getter y setter para acceder y modificar los atributos de la nota.
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

