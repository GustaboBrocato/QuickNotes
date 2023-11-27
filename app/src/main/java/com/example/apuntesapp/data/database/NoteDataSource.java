package com.example.apuntesapp.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.apuntesapp.model.Note;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que actúa como fuente de datos para la gestión de notas en la base de datos SQLite.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): Esta clase sigue el patrón MVC, donde actúa como parte del modelo (Model)
 *   encargada de la gestión de datos. Proporciona métodos para interactuar con la base de datos SQLite.
 *
 * - Clean Architecture: Sigue los principios de Clean Architecture al separar la capa de datos de la lógica de negocio.
 *   La clase se encarga específicamente de operaciones de base de datos relacionadas con las notas.
 *
 * - Model-View-ViewModel (MVVM): Aunque esta clase no implementa directamente el patrón MVVM, es una parte esencial
 *   del modelo (Model) en una arquitectura que sigue los principios de MVVM. Gestiona la fuente de datos y proporciona
 *   datos para la capa de ViewModel.
 *
 * - Custom Views: No utiliza directamente Custom Views. En cambio, se centra en la manipulación de datos y operaciones
 *   relacionadas con la base de datos.
 */

public class NoteDataSource {

    private SQLiteDatabase database;
    private NoteDatabaseHelper dbHelper;

    /**
     * Constructor de la clase.
     *
     * @param context Contexto de la aplicación.
     */
    public NoteDataSource(Context context) {
        dbHelper = new NoteDatabaseHelper(context);
    }

    /**
     * Abre la base de datos en modo escritura.
     *
     * @throws SQLException Excepción lanzada en caso de error al abrir la base de datos.
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Cierra la base de datos.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Inserta una nueva nota en la base de datos.
     *
     * @param note Objeto Note que contiene la información de la nota a insertar.
     * @return El ID de la nueva nota insertada.
     */
    public long insertNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteDatabaseHelper.COLUMN_TITLE, note.getTitle());
        values.put(NoteDatabaseHelper.COLUMN_CONTENT, note.getContent());
        return database.insert(NoteDatabaseHelper.TABLE_NAME, null, values);
    }

    /**
     * Obtiene todas las notas almacenadas en la base de datos.
     *
     * @return Lista de objetos Note que representan todas las notas.
     */
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(
                NoteDatabaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(NoteDatabaseHelper.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(NoteDatabaseHelper.COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(NoteDatabaseHelper.COLUMN_CONTENT));

            Note note = new Note();
            note.setId(id);
            note.setTitle(title);
            note.setContent(content);

            notes.add(note);
        }

        cursor.close();
        return notes;
    }

    /**
     * Actualiza una nota existente en la base de datos.
     *
     * @param note Objeto Note que contiene la información actualizada de la nota.
     * @return True si la actualización fue exitosa, false en caso contrario.
     */
    public boolean updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteDatabaseHelper.COLUMN_TITLE, note.getTitle());
        values.put(NoteDatabaseHelper.COLUMN_CONTENT, note.getContent());

        String whereClause = NoteDatabaseHelper.COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(note.getId())};

        database.update(NoteDatabaseHelper.TABLE_NAME, values, whereClause, whereArgs);
        return true;
    }

    /**
     * Elimina una nota de la base de datos.
     *
     * @param note Objeto Note que representa la nota a eliminar.
     */
    public void deleteNote(Note note) {
        String whereClause = NoteDatabaseHelper.COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(note.getId())};

        database.delete(NoteDatabaseHelper.TABLE_NAME, whereClause, whereArgs);
    }
}



