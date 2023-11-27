package com.example.apuntesapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apuntesapp.data.database.NoteDataSource;
import com.example.apuntesapp.model.Note;

import java.util.List;

/**
 * ViewModel para la gestión de datos de notas y comunicación con la interfaz de usuario.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La clase sigue el patrón MVC, ya que actúa como el controlador (Controller) que
 *   gestiona la lógica de negocio y comunica la vista (View) con los datos (Model).
 *
 * - Model-View-ViewModel (MVVM): Esta clase sigue el patrón MVVM al extender la clase ViewModel y proporcionar
 *   LiveData para la comunicación entre la vista y los datos. LiveData se utiliza para observar y actualizar la
 *   interfaz de usuario de manera reactiva cuando cambian los datos.
 *
 * - Clean Architecture: La clase sigue los principios de Clean Architecture al separar las responsabilidades y
 *   tener una capa de datos (NoteDataSource) que gestiona el acceso a la base de datos.
 *
 * - Custom Views: Aunque esta clase en sí no utiliza Custom Views, interactúa con LiveData, que es una parte
 *   fundamental en la implementación de patrones de observación y actualización en Android.
 */
public class NoteViewModel extends ViewModel {
    // MutableLiveData para la comunicación con la vista
    private MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    private NoteDataSource noteDataSource;

    /**
     * Obtiene LiveData para observar cambios en la lista de notas.
     *
     * @return LiveData que contiene la lista actual de notas.
     */
    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    /**
     * Inicializa el NoteDataSource en el ViewModel.
     *
     * @param dataSource Fuente de datos para acceder a la base de datos.
     */
    public NoteViewModel(NoteDataSource dataSource) {
        Log.e("Lugar: ", "Aqui2");
        this.noteDataSource = dataSource;
    }

    /**
     * Constructor vacío requerido por ViewModel.
     */
    public NoteViewModel() {
    }

    /**
     * Método para actualizar LiveData con una nueva lista de notas.
     *
     * @param notes Nueva lista de notas.
     */
    public void updateNotes(List<Note> notes) {
        notesLiveData.setValue(notes);
    }

    /**
     * Método para actualizar la lista de notas en LiveData después de actualizar una nota.
     */
    public void updateNote() {
        // Actualiza LiveData después del update
        List<Note> updatedNotes = noteDataSource.getAllNotes();
        updateNotes(updatedNotes);
    }

    public void deleteNote() {
        // Actualiza LiveData después del update
        List<Note> updatedNotes = noteDataSource.getAllNotes();
        updateNotes(updatedNotes);
    }
}

