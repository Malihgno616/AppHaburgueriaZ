package com.example.apphaburgueriaz;

import android.os.Bundle;
import android.util.Log;  // IMPORTANTE: adicionar esta importação
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;  // Para mensagens popup
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

interface Components {
    void startComponents();
    void calculate();
}

public class MainActivity extends AppCompatActivity implements Components {
    public double priceBurguer = 20.00;
    public double[] prices = {5.00, 4.00, 7.00, 3.00, 4.50};
    public int quantity = 1;
    public double totalPrice = 0.00;
    public TextView txtQuantity;
    public TextView txtTotalPrice;
    public EditText orderInput;
    public CheckBox[] checkBoxes;

    @Override
    public void startComponents() {
        txtQuantity = findViewById(R.id.quantity);
        txtTotalPrice = findViewById(R.id.totalPrice);
        orderInput = findViewById(R.id.pedidoInput);
        checkBoxes = new CheckBox[] {
                findViewById(R.id.checkBacon),
                findViewById(R.id.checkCheese),
                findViewById(R.id.checkOnion),
                findViewById(R.id.checkKetchup),
                findViewById(R.id.checkBbq)
        };

        for(int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calculate();
                }
            });
        }

        txtQuantity.setText(String.valueOf(quantity));
        txtTotalPrice.setText(String.format("R$ %.2f", totalPrice));

        Button add = findViewById(R.id.buttonPlus);
        Button sub = findViewById(R.id.buttonSub);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
                calculate();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity > 0) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                    calculate();
                }
            }
        });

        calculate();
    }

    @Override
    public void calculate() {
            totalPrice = 0.00;

            for(int i = 0; i < checkBoxes.length; i++) {
                if(checkBoxes[i] != null && checkBoxes[i].isChecked()) {
                    totalPrice += prices[i];
                }
            }

            totalPrice += priceBurguer * quantity;
            txtTotalPrice.setText(String.format("R$ %.2f", totalPrice));
    }

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

        startComponents();
    }
}