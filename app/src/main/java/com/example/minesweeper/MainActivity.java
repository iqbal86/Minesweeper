package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MinesBoard myboard;
    boolean markmode=false;
    TextView NoofMarkedMines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myboard= (MinesBoard) findViewById(R.id.MinesBoard);

        Button resetButton= (Button) findViewById(R.id.ResetButton);
        Button markButton = (Button) findViewById(R.id.MarkButton);



        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    myboard.acceptInput=true;
                toastMessage("Board Reseted!");
                myboard.initMinesArray();
                myboard.invalidate();
                updatenumbers();

            }
        });
        markButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!markmode){
                    markButton.setText("Mark Mode");
                    markmode=true;

                }else{
                    markButton.setText("Uncover Mode");
                    markmode=false;

                }
                myboard.markMode=markmode;

                toastMessage("Mode Switched!");



            }
        });
    }
    public void updatenumbers(){
        NoofMarkedMines= (TextView) findViewById(R.id.markedNo_mines);
        int count=myboard.getMarkedMines();
        NoofMarkedMines.setText(String.valueOf(count));
        //toastMessage("Getting Here!");
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}