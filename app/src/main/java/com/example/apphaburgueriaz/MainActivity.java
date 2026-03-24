package com.example.apphaburgueriaz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public Double priceBurguer = 20.00;

    public Integer quantity = 0;

    public Double totalPrice = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView txtQuantity = findViewById(R.id.quantity);

        EditText orderInput = findViewById(R.id.pedidoInput);

        CheckBox[] checkBoxes= {
                findViewById(R.id.checkBacon),
                findViewById(R.id.checkCheese),
                findViewById(R.id.checkOnion),
                findViewById(R.id.checkKetchup),
                findViewById(R.id.checkBbq)
        };

        Button add = findViewById(R.id.buttonPlus);

        Button sub = findViewById(R.id.buttonSub);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity--;

                if(quantity < 0) {
                    quantity = 0;
                }

                txtQuantity.setText(String.valueOf(quantity));
            }
        });
    }

}