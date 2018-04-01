package com.herokuapp.raydashapp.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int price = 5;
    int cream = 1;
    int chocolate = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = findViewById(R.id.whipped_cream);
        CheckBox chocolate = findViewById(R.id.chocolate);
        EditText customerNameView = findViewById(R.id.customer_name);
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolate.isChecked();
        String customerName = customerNameView.getText().toString();
//        String quantityTextView = ((TextView) findViewById(R.id.quantity_text_view)).getText().toString();
//        int quantity = Integer.parseInt(quantityTextView);
        String success = "Order Placed!";
        String error = "Quantity must be a valid number!";
        int orderTotal = calculatePrice(hasWhippedCream, hasChocolate);
//        if(quantity > 0) {
//            displayMessage(success);
//            displayPrice(orderTotal, hasWhippedCream, hasChocolate, customerName);
//        }else {
//            displayMessage(error);
//            displayPrice(orderTotal, hasWhippedCream, hasChocolate, customerName);
//            return;
//        }
        String bodyText = displayPrice(orderTotal, hasWhippedCream, hasChocolate, customerName);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " order for " +  customerName);
        intent.putExtra(Intent.EXTRA_TEXT, bodyText);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public int calculatePrice(Boolean cream_topping, Boolean chocolate_topping) {
        return quantity * (price + (cream_topping ? cream : 0) + (chocolate_topping ? chocolate : 0));
    }


    public void increment(View view){
//        String quantityTextView = ((TextView) findViewById(R.id.quantity_text_view)).getText().toString();
//        int quantity = Integer.parseInt(quantityTextView);
        quantity++;
        display(quantity);
    }

    public void decrement(View view){
//        String quantityTextView = ((TextView) findViewById(R.id.quantity_text_view)).getText().toString();
//        int quantity = Integer.parseInt(quantityTextView);
        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private String displayPrice(int number, Boolean cream, Boolean chocolate, String customer_name) {
        TextView OrderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        OrderSummaryTextView.setText("Name: "+ customer_name +"\nQuantity: "+ quantity +"\nTotal: " + NumberFormat.getCurrencyInstance(Locale.US).format(number) + "\nAdd whipped cream? " + cream +"\nAdd Chocolate? " + chocolate +"\nThank You!");
        return getString(R.string.order_summary_name, customer_name) +"\n" + getString(R.string.order_quantity, quantity) +"\n"+ getString(R.string.order_total_price, NumberFormat.getCurrencyInstance(Locale.US).format(number)) +"\n"+ getString(R.string.add_cream, cream) + "\n" + getString(R.string.add_chocolate, chocolate) + "\n" + getString(R.string.thank_you);
    }

    private void displayMessage(String message){
        TextView messageTextView = findViewById(R.id.message_text_view);
        messageTextView.setText(message);
    }
}
