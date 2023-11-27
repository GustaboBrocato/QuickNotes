package com.example.apuntesapp.ui.addnote;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.apuntesapp.R;
import com.example.apuntesapp.data.database.NoteDataSource;
import com.example.apuntesapp.model.Note;

/**
 * Actividad para agregar nuevas notas.
 *
 * Esta actividad permite al usuario ingresar un título y contenido para una nueva nota,
 * la cual se guarda en la base de datos SQLite.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La actividad sigue el patrón MVC donde la interfaz de usuario
 *   (View) interactúa con la lógica de negocio (Controller) representada por la clase.
 *   [Nota: Aunque la estructura es MVC, la arquitectura general sigue patrones de MVVM y Clean Architecture]
 *
 * - Model-View-ViewModel (MVVM): Aunque sigue un enfoque MVC, la aplicación utiliza
 *   conceptos de MVVM en la capa de ViewModel para manejar la lógica de presentación y
 *   gestionar la comunicación entre la vista y los datos.
 *
 * - Clean Architecture: La aplicación sigue principios de Clean Architecture al dividir
 *   las responsabilidades en capas: UI (Vista), Casos de Uso (Lógica de Negocio), y Datos (Acceso a la base de datos).
 *
 * - Custom Views: La actividad no utiliza vistas personalizadas, pero hace uso de componentes
 *   de la interfaz de usuario de Android (EditText, Button) para la entrada y la interacción del usuario.
 */

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private NoteDataSource noteDataSource;
    private ImageButton btnAtras;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnAtras = findViewById(R.id.imagebuttonAddAtras);

        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene el contenido de los EditText
                String title = editTextTitle.getText().toString().trim();
                String content = editTextContent.getText().toString().trim();

                // Revisa si el tituto y contenido no estan vacios
                if (!title.isEmpty() && !content.isEmpty()) {
                    // Crea una nueva nota con el contenido
                    Note newNote = new Note();
                    newNote.setTitle(title);
                    newNote.setContent(content);

                    // Inserta la nota en la base de datos
                    long insertedId = noteDataSource.insertNote(newNote);

                    if (insertedId != -1) {
                        //Nota se ingreso
                        Toast.makeText(AddNoteActivity.this, "¡Nota Agregada!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Fallo
                        Toast.makeText(AddNoteActivity.this, "No se pudo agregar la nota.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(AddNoteActivity.this, "Porfavor ingrese un titulo y contenido.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Cierra sql lite cuando la actividad es destruida
        noteDataSource.close();
    }
}
