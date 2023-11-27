package com.example.apuntesapp.ui.notedetail;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.apuntesapp.R;
import com.example.apuntesapp.model.Note;
import com.example.apuntesapp.ui.customview.NoteView;

/**
 * Actividad que muestra detalles de una nota específica.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La clase actúa como parte de la vista (View) en el patrón MVC.
 *   Es responsable de la presentación y visualización de detalles de una nota en la interfaz de usuario.
 *
 * - Custom Views: La actividad utiliza un diseño personalizado (NoteView) para representar visualmente
 *   los detalles de una nota en la interfaz de usuario.
 *
 * - Clean Architecture: Sigue los principios de Clean Architecture al centrarse en la presentación de detalles
 *   de una nota en la interfaz de usuario sin realizar lógica de negocio compleja.
 *
 * - Model-View-ViewModel (MVVM): Aunque la clase se centra en la visualización (View) de detalles de una nota
 *   y no implementa directamente el patrón MVVM, puede ser utilizada en conjunto con ViewModel para actualizar
 *   la interfaz de usuario en respuesta a cambios en los datos.
 */
public class NoteDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_TITLE = "extra_note_title";
    public static final String EXTRA_NOTE_CONTENT = "extra_note_content";

    /**
     * Método llamado al crear la actividad.
     *
     * @param savedInstanceState Objeto que contiene el estado previamente guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        // Busca la vista personalizada NoteView en el diseño de la actividad
        NoteView noteView = findViewById(R.id.customNoteView);

        // Obtiene los detalles de la nota desde el intent
        String title = getIntent().getStringExtra(EXTRA_NOTE_TITLE);
        String content = getIntent().getStringExtra(EXTRA_NOTE_CONTENT);

        // Crea un objeto Note con los detalles de la nota
        Note note = new Note(title, content); // Suponiendo que tienes un constructor en la clase Note

        // Configura la vista personalizada NoteView con los datos de la nota
        noteView.setNoteData(note);
    }
}
