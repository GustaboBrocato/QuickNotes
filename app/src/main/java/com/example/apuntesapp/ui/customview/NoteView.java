package com.example.apuntesapp.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apuntesapp.R;
import com.example.apuntesapp.model.Note;

/**
 * Vista personalizada utilizada para mostrar los detalles de una nota en la interfaz de usuario.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La clase actúa como parte de la vista (View) en el patrón MVC.
 *   Se encarga de presentar la información de una nota en la interfaz de usuario.
 *
 * - Custom Views: Representa un ejemplo de vista personalizada que encapsula la presentación
 *   de detalles de una nota, facilitando la reutilización y modularidad en la interfaz de usuario.
 *
 * - Clean Architecture: Sigue los principios de Clean Architecture al centrarse en la presentación
 *   de detalles de una nota en la interfaz de usuario sin realizar lógica de negocio compleja.
 *
 * - Model-View-ViewModel (MVVM): Aunque la clase se centra en la visualización (View) de detalles
 *   de una nota y no implementa directamente el patrón MVVM, puede ser utilizada en conjunto con
 *   ViewModel para actualizar la interfaz de usuario en respuesta a cambios en los datos.
 */
public class NoteView extends LinearLayout {

    private TextView titleTextView;
    private TextView contentTextView;

    /**
     * Constructores de la clase.
     */
    public NoteView(Context context) {
        super(context);
        init();
    }

    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Método privado utilizado para realizar la inicialización de la vista personalizada.
     */
    private void init() {
        // Infla el diseño de la vista personalizada desde el archivo XML
        LayoutInflater.from(getContext()).inflate(R.layout.custom_note_view, this, true);

        // Obtiene referencias a los elementos de la vista
        titleTextView = findViewById(R.id.customNoteTitle);
        contentTextView = findViewById(R.id.customNoteContent);
    }

    /**
     * Método público utilizado para establecer los datos de una nota en la vista.
     *
     * @param note Objeto Note que contiene los detalles de la nota.
     */
    public void setNoteData(Note note) {
        // Establece el texto del título y contenido de la nota en los TextView correspondientes
        titleTextView.setText(note.getTitle());
        contentTextView.setText(note.getContent());
    }
}


