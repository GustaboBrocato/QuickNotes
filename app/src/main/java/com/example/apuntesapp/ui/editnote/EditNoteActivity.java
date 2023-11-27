package com.example.apuntesapp.ui.editnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.apuntesapp.R;
import com.example.apuntesapp.data.database.NoteDataSource;
import com.example.apuntesapp.model.Note;
import com.example.apuntesapp.ui.addnote.AddNoteActivity;
import com.example.apuntesapp.viewmodel.NoteViewModel;

/**
 * Actividad para editar una nota existente.
 *
 * Esta actividad permite al usuario realizar cambios en el título y contenido de una nota ya existente.
 * Los cambios se reflejan tanto en la base de datos como en la interfaz de usuario.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La actividad sigue el patrón MVC donde la interfaz de usuario
 *   (View) interactúa con la lógica de negocio (Controller) representada por la clase.
 *
 * - Model-View-ViewModel (MVVM): La actividad utiliza el ViewModel (NoteViewModel) para manejar la lógica
 *   de presentación y gestionar la comunicación entre la vista y los datos. Se aprecia en la actualización
 *   de la nota a través del ViewModel.
 *
 * - Clean Architecture: La aplicación sigue principios de Clean Architecture al dividir
 *   las responsabilidades en capas: UI (Vista), Casos de Uso (Lógica de Negocio), y Datos (Acceso a la base de datos).
 *
 * - Custom Views: La actividad no utiliza vistas personalizadas, pero hace uso de componentes
 *   de la interfaz de usuario de Android (EditText, Button) para la entrada y la interacción del usuario.
 */
public class EditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "com.example.apuntesapp.EXTRA_NOTE_ID";
    public static final String EXTRA_NOTE_TITLE = "com.example.apuntesapp.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_CONTENT = "com.example.apuntesapp.EXTRA_NOTE_CONTENT";

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSave;
    private NoteDataSource noteDataSource;
    private NoteViewModel noteViewModel;
    private ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Inicializa los componentes de la interfaz de usuario
        editTextTitle = findViewById(R.id.editTextTitleEditar);
        editTextContent = findViewById(R.id.editTextContentEditar);
        buttonSave = findViewById(R.id.buttonSave);
        btnAtras = findViewById(R.id.imagebuttonActAtras);

        // Inicializa el origen de datos de la nota (NoteDataSource)
        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();

        // Inicializa el ViewModel para la lógica de presentación
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        // Obtiene la información del intent
        long noteId = getIntent().getLongExtra(EXTRA_NOTE_ID, -1);
        String noteTitle = getIntent().getStringExtra(EXTRA_NOTE_TITLE);
        String noteContent = getIntent().getStringExtra(EXTRA_NOTE_CONTENT);

        // Coloca la información original en los EditText
        editTextTitle.setText(noteTitle);
        editTextContent.setText(noteContent);

        // Configura un listener para el botón de guardar
        buttonSave.setOnClickListener(view -> saveNoteChanges(noteId));

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveNoteChanges(long noteId) {
        String newTitle = editTextTitle.getText().toString();
        String newContent = editTextContent.getText().toString();

        if (!newTitle.isEmpty() && !newContent.isEmpty()) {
            // Crea una nueva nota con la información actualizada
            Note newNote = new Note();
            newNote.setTitle(newTitle);
            newNote.setContent(newContent);
            newNote.setId(noteId);

            // Actualiza la nota en la base de datos
            if (noteDataSource.updateNote(newNote)) {
                // Nota actualizada exitosamente
                Toast.makeText(EditNoteActivity.this, "¡Nota Actualizada!", Toast.LENGTH_SHORT).show();
                // Actualiza la información en el ViewModel
                noteViewModel.updateNote();
                finish();
            } else {
                // Fallo en la actualización de la nota
                Toast.makeText(EditNoteActivity.this, "No se pudo actualizar la nota", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else {
            Toast.makeText(EditNoteActivity.this, "Porfavor ingrese un titulo y contenido.", Toast.LENGTH_SHORT).show();
        }
    }
}
