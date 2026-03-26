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

interface Components {
    void startComponents();
    void calculate();
    void sendMail();
    void showPreview();
}

public class MainActivity extends AppCompatActivity implements Components {
    public double priceBurguer = 20.00;
    public double[] prices = {5.00, 4.00, 7.00, 3.00, 4.50};
    public int quantity = 0;
    public double totalPrice = 0.00;
    public TextView txtQuantity;
    public TextView txtTotalPrice;
    public EditText clientInput;
    public CheckBox[] checkBoxes;
    public TextView[] previews;

    @Override
    public void startComponents() {
        txtQuantity = findViewById(R.id.quantity);
        txtTotalPrice = findViewById(R.id.totalPrice);
        clientInput = findViewById(R.id.clientInput);
        checkBoxes = new CheckBox[] {
                findViewById(R.id.checkBacon),
                findViewById(R.id.checkCheese),
                findViewById(R.id.checkOnion),
                findViewById(R.id.checkKetchup),
                findViewById(R.id.checkBbq)
        };

        previews = new TextView[] {
                findViewById(R.id.namePreview),
                findViewById(R.id.baconPreview),
                findViewById(R.id.cheesePreview),
                findViewById(R.id.onionPreview),
                findViewById(R.id.ketchupPreview),
                findViewById(R.id.bbqPreview),
                findViewById(R.id.qttPreview),
                findViewById(R.id.totalPreview)
        };

        for(int i = 0; i < previews.length; i++) {
            previews[i].setVisibility(View.GONE);
        }

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
                showPreview();

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
    public void showPreview() {
        if(quantity > 0) {
            for (int i = 0; i < previews.length; i++) {
                previews[i].setVisibility(View.VISIBLE);
            }

            String clientName = clientInput.getText().toString().trim();
            if(clientName.isEmpty()) {
                clientName = "Cliente não informado";
            }
            previews[0].setText("Nome do cliente: " + clientName);

        } else {
            for (int i = 0; i < previews.length; i++) {
                previews[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void sendMail() {

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