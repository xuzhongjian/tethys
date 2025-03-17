package com.ganten.tethys.settle;

public class Order {
    private double gainAmount, payAmount;
    private String gainCurrency, payCurrency;

    public Order(double gainAmount, String gainCurrency, double pay, String payCurrency) {
        this.gainAmount = gainAmount;
        this.gainCurrency = gainCurrency;
        this.payAmount = pay;
        this.payCurrency = payCurrency;
    }

    @Override
    public String toString() {
        return "支付 " + payAmount + " " + payCurrency + ", 获得 " + gainAmount + " " + gainCurrency;
    }

    public double getGainAmount() {
        return gainAmount;
    }

    public void setGainAmount(double gainAmount) {
        this.gainAmount = gainAmount;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getGainCurrency() {
        return gainCurrency;
    }

    public void setGainCurrency(String gainCurrency) {
        this.gainCurrency = gainCurrency;
    }

    public String getPayCurrency() {
        return payCurrency;
    }

    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency;
    }
}