package com.example.apphaburgueriaz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

interface Components {
    void startComponents();
    void calculate();
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
    public ActivityResultLauncher<Intent> emailLauncher;

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
                    showPreview();
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
                    showPreview();
                }
            }
        });

        calculate();

        Button sendEmail = findViewById(R.id.sendOrder);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity == 0) {
                    Toast.makeText(MainActivity.this,
                            "Adicione pelo menos um hambúrguer",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String clientName = clientInput.getText().toString().trim();
                if(clientName.isEmpty()) {
                    clientName = "Cliente não informado";
                }

                StringBuilder emailBody = new StringBuilder();
                emailBody.append("Nome do cliente: " + clientName).append("\n");
                emailBody.append("Tem Bacon?: " + (checkBoxes[0].isChecked() ? "Sim" : "Não")).append("\n");
                emailBody.append("Tem Queijo?: " + (checkBoxes[1].isChecked() ? "Sim" : "Não")).append("\n");
                emailBody.append("Tem Onion Rings?: " + (checkBoxes[2].isChecked() ? "Sim" : "Não")).append("\n");
                emailBody.append("Tem Ketchup?: " + (checkBoxes[3].isChecked() ? "Sim" : "Não")).append("\n");
                emailBody.append("Tem Barbecue?: " + (checkBoxes[4].isChecked() ? "Sim" : "Não")).append("\n");
                emailBody.append("Quantidade: " + quantity).append("\n");
                emailBody.append("Total: R$ " + String.format("%.2f", totalPrice));

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ackinlino123@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de (" + clientName + ")");
                intent.putExtra(Intent.EXTRA_TEXT, emailBody.toString());

                try {
                    emailLauncher.launch(Intent.createChooser(intent, "Enviar e-mail"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this,
                    "Não há app de e-mail instalado",
                    Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            previews[1].setText("Tem Bacon?: " + (checkBoxes[0].isChecked() ? "Sim" : "Não"));
            previews[2].setText("Tem Queijo?: " + (checkBoxes[1].isChecked() ? "Sim" : "Não"));
            previews[3].setText("Tem Onion Rings?: " + (checkBoxes[2].isChecked() ? "Sim" : "Não"));
            previews[4].setText("Tem Ketchup?: " + (checkBoxes[3].isChecked() ? "Sim" : "Não"));
            previews[5].setText("Tem Barbecue?: " + (checkBoxes[4].isChecked() ? "Sim" : "Não"));
            previews[6].setText("Quantidade: " + quantity);
            previews[7].setText("Total: R$ " + String.format("%.2f", totalPrice));

        } else {
            for (int i = 0; i < previews.length; i++) {
                previews[i].setVisibility(View.GONE);
            }
        }
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

        emailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Toast.makeText(MainActivity.this,
                                "Pedido enviado com sucesso!",
                                Toast.LENGTH_LONG).show();
                        resetFields();
                    }
                }
        );

        startComponents();
    }

    private void resetFields() {
        quantity = 0;

        txtQuantity.setText("0");

        for(int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setChecked(false);
        }

        clientInput.setText("");

        calculate();

        showPreview();
    }
}