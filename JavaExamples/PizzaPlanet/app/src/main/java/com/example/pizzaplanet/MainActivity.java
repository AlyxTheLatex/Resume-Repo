package com.example.pizzaplanet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupSize;
    private Spinner spinnerCrust;
    private CheckBox checkPepperoni,checkMushrooms,checkOnions,checkExtraCheese;
    private Button calculateButton;
    private TextView summaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //link your xml to a java object
        // --- Initialize UI elements by finding them by their ID ---
        radioGroupSize = findViewById(R.id.radioGroupSize);
        spinnerCrust = findViewById(R.id.spinnerCrust);
        checkPepperoni = findViewById(R.id.checkPepperoni);
        checkMushrooms = findViewById(R.id.checkMushrooms);
        checkOnions = findViewById(R.id.checkOnions);
        checkExtraCheese = findViewById(R.id.checkExtraCheese);
        calculateButton = findViewById(R.id.calculateButton);
        summaryTextView = findViewById(R.id.summaryTextView);

        //Set our button to build a pizza
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildPizzaAndDisplaySummary();
            }
        });
    }

    //function that builds your pizza
    private void buildPizzaAndDisplaySummary(){
        //1. Create a pizza object
        Pizza myPizza = new Pizza();

        //2. Get selected size
        int selectedSizeId = radioGroupSize.getCheckedRadioButtonId();

        if(selectedSizeId == -1){
            Toast.makeText(this,"Please select a size",Toast.LENGTH_LONG).show();
            return;
        } else{
            RadioButton selectedRadioBTN = findViewById(selectedSizeId);
            myPizza.setSize(selectedRadioBTN.getText().toString());
        }

        //3. Get selected crust
        String crustType = spinnerCrust.getSelectedItem().toString();
        myPizza.setCrust(crustType);

        //4. Get selected toppings
        if(checkExtraCheese.isChecked()){myPizza.addToppings("ExtraCheese");}
        if(checkMushrooms.isChecked()){myPizza.addToppings("Mushrooms");}
        if(checkOnions.isChecked()){myPizza.addToppings("Onions");}
        if(checkPepperoni.isChecked()){myPizza.addToppings("Pepperoni");}

        //5. Display the summary
        summaryTextView.setText(myPizza.getOrderSummary());
    }
}