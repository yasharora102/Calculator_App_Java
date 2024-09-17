package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        // Set button click listeners
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String buttonText = button.getText().toString();

                if (isNumeric(buttonText)) {
                    currentInput += buttonText;
                    tvResult.setText(currentInput);
                } else if (buttonText.equals("C")) {
                    resetCalculator();
                } else if (buttonText.equals("=")) {
                    calculateResult();
                } else if (buttonText.equals("%")) {
                    calculatePercentage();
                } else if (buttonText.equals("±")) {
                    toggleNegative();
                } else {
                    operator = buttonText;
                    firstOperand = Double.parseDouble(currentInput);
                    currentInput = "";
                }
            }
        };

        setButtonListeners(buttonClickListener);
    }

    private void setButtonListeners(View.OnClickListener listener) {
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide,
                R.id.buttonClear, R.id.buttonEquals, R.id.buttonPercent, R.id.buttonSign
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void resetCalculator() {
        currentInput = "";
        operator = "";
        firstOperand = 0;
        tvResult.setText("0");
    }

    private void calculateResult() {
        double secondOperand = Double.parseDouble(currentInput);
        Operation operation = null;

        switch (operator) {
            case "+":
                operation = new Addition(firstOperand, secondOperand);
                break;
            case "−":
                operation = new Subtraction(firstOperand, secondOperand);
                break;
            case "×":
                operation = new Multiplication(firstOperand, secondOperand);
                break;
            case "÷":
                operation = new Division(firstOperand, secondOperand);
                break;
        }

        if (operation != null) {
            double result = operation.calculate();
            tvResult.setText(String.valueOf(result));
            currentInput = "";
        }
    }

    private void calculatePercentage() {
        double value = Double.parseDouble(currentInput);
        value = value / 100;
        tvResult.setText(String.valueOf(value));
        currentInput = String.valueOf(value);
    }

    private void toggleNegative() {
        if (currentInput.startsWith("-")) {
            currentInput = currentInput.substring(1);
        } else {
            currentInput = "-" + currentInput;
        }
        tvResult.setText(currentInput);
    }
}