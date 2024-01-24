package sorcer.arithmetic.provider.impl;

import sorcer.arithmetic.provider.IPayment;

import java.rmi.RemoteException;

public class PaymentsImpl implements IPayment {

    private String paymentOption;

    public PaymentsImpl() {
        this.paymentOption = "";
    }

    public void addPaymentOption(String  paymentOption) {
        setPaymentOption(paymentOption);
    }

    public void pay() throws RemoteException {
       // payment logic
    }

    public String getpaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }
}
