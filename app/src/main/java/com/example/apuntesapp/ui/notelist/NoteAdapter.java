package com.example.apuntesapp.ui.notelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apuntesapp.R;
import com.example.apuntesapp.model.Note;

import java.util.List;

/**
 * Adaptador para la visualización de notas en un RecyclerView.
 *
 * Conceptos:
 * - Model-View-Controller (MVC): La clase actúa como parte de la vista (View) en el patrón MVC.
 *   Es responsable de la presentación y visualización de datos en la interfaz de usuario.
 *
 * - Custom Views: La clase utiliza un diseño personalizado (item_note.xml) para representar visualmente
 *   cada elemento de la lista en el RecyclerView.
 *
 * - Clean Architecture: Sigue los principios de Clean Architecture al centrarse en la presentación de datos
 *   en la interfaz de usuario sin realizar lógica de negocio compleja.
 *
 * - Model-View-ViewModel (MVVM): Aunque la clase se centra en la visualización (View) de datos y no implementa
 *   directamente el patrón MVVM, es parte de la capa de vista y se puede utilizar en conjunción con ViewModel
 *   para actualizar la interfaz de usuario en respuesta a cambios en los datos.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private OnItemClickListener onItemClickListener;

    /**
     * Constructor del adaptador.
     *
     * @param notes Lista de objetos Note que se mostrarán en el RecyclerView.
     */
    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    /**
     * Crea nuevas instancias de NoteViewHolder según sea necesario.
     *
     * @param parent   Grupo de vistas en el que se inflará la nueva vista.
     * @param viewType El tipo de la nueva vista.
     * @return Nueva instancia de NoteViewHolder.
     */
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout noteView = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(noteView);
    }

    /**
     * Llena el contenido de una vista de ViewHolder según su posición en el RecyclerView.
     *
     * @param holder   La vista de ViewHolder que debe actualizarse.
     * @param position La posición del elemento en el conjunto de datos.
     */
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewNoteTitle.setText(note.getTitle());

        // Listener para el botón editar
        holder.btnEditar.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onEditClick(view, position);
            }
        });

        // Listener para el botón eliminar
        holder.btnEliminar.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDeleteClick(view, position);
            }
        });
    }

    /**
     * Devuelve la cantidad de elementos en el conjunto de datos.
     *
     * @return El número total de elementos.
     */
    @Override
    public int getItemCount() {
        return notes.size();
    }

    /**
     * Establece el objeto OnItemClickListener para manejar eventos de clic en los elementos del RecyclerView.
     *
     * @param onItemClickListener Objeto OnItemClickListener.
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Interfaz que define los métodos para manejar eventos de clic en los elementos del RecyclerView.
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onEditClick(View view, int position);
        void onDeleteClick(View view, int position);
    }

    /**
     * Clase interna que representa una vista de elemento en el RecyclerView.
     */
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNoteTitle;
        ImageButton btnEditar, btnEliminar;

        /**
         * Constructor de la clase interna.
         *
         * @param itemView Vista de un elemento en el RecyclerView.
         */
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNoteTitle = itemView.findViewById(R.id.textViewNoteTitle);
            btnEditar = itemView.findViewById(R.id.imageButtonEditar);
            btnEliminar = itemView.findViewById(R.id.imageButtonEliminar);

            // Listener para el clic en el elemento
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
}

