package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MinesBoard myboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myboard= (MinesBoard) findViewById(R.id.MinesBoard);

        Button resetButton= (Button) findViewById(R.id.ResetButton);


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    myboard.acceptInput=true;
                toastMessage("Board Reseted!");
                myboard.initMinesArray();
                myboard.invalidate();

            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}