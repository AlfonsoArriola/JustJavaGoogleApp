package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *  Whipped cream boolean
    * Chocolate boolean
     */

    public boolean hasWhippedCream = false;
    public boolean hasChocolate = false;

    /**
     * This method is called when the order button is clicked.
     */

    public void addCoffee(View view) {
        if(quantity == 100){
            Toast.makeText(this, "no, no. no...", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }
    public void subtractCoffee(View view) {
        if(quantity == 1){
            Toast.makeText(this, "no, no. no...", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    public void submitOrder(View view) {
           EditText inputTxt = (EditText)findViewById(R.id.plain_text_input);
           String name = inputTxt.getText().toString();

           CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
           boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

           CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
           boolean hasChocolate = chocolateCheckBox.isChecked();

           int price = calculatePrice(hasChocolate, hasWhippedCream);
           String priceMessage = createOrderSummary(name,price, hasWhippedCream, hasChocolate);





        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        startActivity(Intent.createChooser(intent, "Coffee Order"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     *  @retun total price
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream) {
        int basePrice = 5;

        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        if (addChocolate){
            basePrice = basePrice + 2;
        }


        return quantity * basePrice;
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     *@param price of order
     *@param addWhippedCream for adding whipped.....umm...cream
     *@return text summary....sonething
     */


private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + calculatePrice(addChocolate, addWhippedCream);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
}





}