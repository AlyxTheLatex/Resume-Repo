package com.example.pizzaplanet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Pizza {
// --- Fields
private String size, crust;
private final ArrayList<String> toppings;
// --- Constants for Pricing
private static final double PRICE_SMALL = 8.00;
private static final double PRICE_MEDIUM = 10.00;
private static final double PRICE_LARGE = 12.00;
private static final double PRICE_PER_TOPPING = 1.00;
private static final double SURCHARGE_DEEP_DISH = 2.00;


    // --- Constructor ---
    public Pizza(){
        this.toppings = new ArrayList<>();
    }

    // --- Getters and Setters ---

    public ArrayList<String> getToppings() {
        return toppings;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void addToppings(String topping){
        this.toppings.add(topping);
    }


    // --- Custom Methods ---
    public double calculatePrice(){
        double totalPrice = 0.0;

        //1. Slice of Pizza
        if(size != null){
            switch (size){
                case "Small":
                    totalPrice+=PRICE_SMALL;
                    break;
                case "Medium":
                    totalPrice+=PRICE_MEDIUM;
                    break;
                case "Large":
                    totalPrice+=PRICE_LARGE;
                    break;
            }
        }

        //2. Crust Type
        if(crust!=null && crust.equals("Deep DIsh")){
            totalPrice += SURCHARGE_DEEP_DISH;
        }

        //3. Add Toppings
        totalPrice += this.toppings.size() * PRICE_PER_TOPPING;

        return totalPrice;
    }

    // --- toString ---
    public String getOrderSummary() {
        double totalPrice = calculatePrice();
        // Use NumberFormat to format the price as currency (e.g., $13.50)
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        String formattedPrice = currencyFormatter.format(totalPrice);
        // Use StringBuilder for efficient string concatenation
        StringBuilder summary = new StringBuilder();
        summary.append("ORDER SUMMARY\n");
        summary.append("-----------------\n");
        summary.append("Size: ").append(size).append("\n");
        summary.append("Crust: ").append(crust).append("\n");
        // List all selected toppings
        if (toppings.isEmpty()) {
            summary.append("Toppings: None\n");
        } else {
            summary.append("Toppings:\n");
            for (String topping : toppings) {
                summary.append(" - ").append(topping).append("\n");
            }
        }
        summary.append("-----------------\n");
        summary.append("TOTAL: ").append(formattedPrice).append("\n");
        return summary.toString();
    }
}
