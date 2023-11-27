package com.example.apuntesapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.apuntesapp.ui.notelist.NoteListActivity;
import com.example.apuntesapp.ui.addnote.AddNoteActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnViewNotes = findViewById(R.id.btnViewNotes);
        Button btnAddNote = findViewById(R.id.btnAddNote);

        btnViewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch NoteListActivity to view all notes
                startActivity(new Intent(MainActivity.this, NoteListActivity.class));
            }
        });

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch AddNoteActivity to add a new note
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
            }
        });
    }
}
