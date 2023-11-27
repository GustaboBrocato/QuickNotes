package com.example.apuntesapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase que actúa como un ayudante para gestionar la creación y actualización de la base de datos
 * SQLite utilizada para almacenar notas.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La clase sigue el patrón MVC al proporcionar un componente (Model)
 *   que se ocupa de la creación y actualización de la base de datos, así como de la definición de
 *   la estructura de la tabla de notas.
 *
 * - Clean Architecture: Adhiere a los principios de Clean Architecture al separar las operaciones
 *   de la base de datos en una clase independiente, permitiendo una clara separación entre la lógica
 *   de la base de datos y otras capas de la aplicación.
 *
 * - Model-View-ViewModel (MVVM): Aunque no está directamente relacionada con el patrón MVVM, la
 *   clase contribuye a la capa de Model al definir la estructura de la base de datos y cómo interactúa
 *   con la lógica de persistencia de datos.
 *
 * - Custom Views: Esta clase no es una vista personalizada, sino una utilidad para la gestión de la
 *   base de datos. Sin embargo, al manejar la persistencia de datos, puede ser crucial para el
 *   soporte de vistas personalizadas que muestran datos almacenados.
 */
public class NoteDatabaseHelper extends SQLiteOpenHelper {

    // Define el nombre y la versión de la base de datos
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    // Define el nombre de la tabla y las columnas
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";

    // Define la instrucción SQL para crear la tabla
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_CONTENT + " TEXT)";

    /**
     * Constructor que inicializa el ayudante de la base de datos.
     *
     * @param context Contexto de la aplicación.
     */
    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Método llamado cuando se crea la base de datos por primera vez.
     *
     * @param db Base de datos SQLite.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla cuando se crea la base de datos
        db.execSQL(SQL_CREATE_TABLE);
    }

    /**
     * Método llamado cuando la versión de la base de datos cambia.
     *
     * @param db         Base de datos SQLite.
     * @param oldVersion Versión antigua de la base de datos.
     * @param newVersion Nueva versión de la base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Maneja las actualizaciones de la base de datos aquí si es necesario
        // Para simplificar, eliminaremos y recrearemos la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}



