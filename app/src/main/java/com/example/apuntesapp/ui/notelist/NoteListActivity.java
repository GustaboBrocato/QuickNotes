package com.example.apuntesapp.ui.notelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.apuntesapp.R;
import com.example.apuntesapp.data.database.NoteDataSource;
import com.example.apuntesapp.model.Note;
import com.example.apuntesapp.ui.editnote.EditNoteActivity;
import com.example.apuntesapp.ui.notedetail.NoteDetailActivity;
import com.example.apuntesapp.viewmodel.NoteViewModel;

import java.util.List;

/**
 * Actividad que muestra una lista de notas y permite realizar operaciones como ver detalles, editar y eliminar.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La actividad sigue el patrón MVC, donde la interfaz de usuario (View) interactúa
 *   con la lógica de negocio (Controller) representada por la clase.
 *
 * - Model-View-ViewModel (MVVM): Se utiliza ViewModel (NoteViewModel) para manejar la lógica de presentación
 *   y gestionar la comunicación entre la vista y los datos. Se observa en la actualización del RecyclerView con
 *   la lista de notas desde el ViewModel.
 *
 * - Clean Architecture: La aplicación sigue principios de Clean Architecture al dividir las responsabilidades
 *   en capas: UI (Vista), Casos de Uso (Lógica de Negocio), y Datos (Acceso a la base de datos).
 *
 * - Custom Views: La actividad utiliza un RecyclerView personalizado (NoteAdapter) para mostrar la lista de notas.
 */

public class NoteListActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private NoteDataSource noteDataSource;
    private List<Note> notes;
    private RecyclerView recyclerView;
    private ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        btnAtras = findViewById(R.id.imagebuttonListaAtras);

        // Inicializa ViewModel
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        // Abre SQLite database
        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();

        // Configura el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtiene las notas de SQLite database
        List<Note> initialNotes = noteDataSource.getAllNotes();

        // Actualiza el ViewModel con las notas iniciales
        noteViewModel.updateNotes(initialNotes);

        //Listener boton atras
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Observa cambios en notesLiveData
        noteViewModel.getNotesLiveData().observe(this, newNotes -> {
            // Actualiza el UI con una nueva lista de notas
            notes = newNotes;
            NoteAdapter noteAdapter = new NoteAdapter(notes);
            recyclerView.setAdapter(noteAdapter);

            noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // Maneja el clic para ver detalles de una nota
                    Note clickedNote = notes.get(position);
                    Intent intent = new Intent(NoteListActivity.this, NoteDetailActivity.class);
                    intent.putExtra(NoteDetailActivity.EXTRA_NOTE_TITLE, clickedNote.getTitle());
                    intent.putExtra(NoteDetailActivity.EXTRA_NOTE_CONTENT, clickedNote.getContent());
                    startActivity(intent);
                }

                @Override
                public void onEditClick(View view, int position) {
                    // Maneja el clic en el botón de editar
                    Note clickedNote = notes.get(position);

                    // Inicia la actividad para editar la nota
                    Intent intent = new Intent(NoteListActivity.this, EditNoteActivity.class);
                    intent.putExtra(EditNoteActivity.EXTRA_NOTE_ID, clickedNote.getId());
                    intent.putExtra(EditNoteActivity.EXTRA_NOTE_TITLE, clickedNote.getTitle());
                    intent.putExtra(EditNoteActivity.EXTRA_NOTE_CONTENT, clickedNote.getContent());
                    startActivity(intent);
                }

                @Override
                public void onDeleteClick(View view, int position) {
                    // Muestra un diálogo de confirmación antes de eliminar la nota
                    showDeleteConfirmationDialog(position);
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cierra SQLite database cuando la actividad es destruida
        noteDataSource.close();
    }

    /**
     * Muestra un diálogo de confirmación para eliminar una nota.
     *
     * @param position Posición de la nota en la lista.
     */
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Eliminar Nota");
        builder.setMessage("¿Está seguro de que desea eliminar la nota?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            // Borra la nota si se confirma la eliminación
            deleteNoteAtPosition(position);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            // Cancela el diálogo si se elige no eliminar
            dialog.dismiss();
        });
        builder.create().show();
    }

    /**
     * Elimina una nota en la posición dada.
     *
     * @param position Posición de la nota en la lista.
     */
    private void deleteNoteAtPosition(int position) {
        Note noteToDelete = notes.get(position);
        noteDataSource.deleteNote(noteToDelete);

        // Actualiza el UI
        notes.remove(position);
        recreate();
    }
}

