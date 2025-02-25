package com.docencia.tutorial.controllers;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;


public class ProductForm {


    @NotEmpty(message = "The product name is required")

    private String name;


    @NotNull(message = "The price is required")

    @Min(value = 1, message = "The price must be greater than 0")

    private Double price;


    public String getName() {

        return name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public Double getPrice() {

        return price;

    }


    public void setPrice(Double price) {

        this.price = price;

    }

}