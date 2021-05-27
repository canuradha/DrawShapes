package com.example.drawshapes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DrawActivity extends AppCompatActivity {

    CanvasView canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        canvas = new CanvasView(this);
        canvas.setBackgroundColor(Color.WHITE);
        setContentView(canvas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.draw_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.line:
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                this.canvas.setActivityType(1);
                break;
            case R.id.triangle:
//                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
//                        .show();
                this.canvas.setActivityType(2);
                break;
            case R.id.rectangle:
                this.canvas.setActivityType(3);
                break;
            case R.id.circle:
                this.canvas.setActivityType(4);
                break;
            case R.id.clear:
                this.canvas.clearCanvas();
                this.canvas.requestFocus();
                break;
            case  R.id.Undo:
                this.canvas.undoLast();
                this.canvas.requestFocus();
                break;
            default:
                this.canvas.setActivityType(0);
                break;
        }

        return true;
    }
}