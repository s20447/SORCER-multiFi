package sorcer.arithmetic.provider.impl;

public class PaymentsImpl implements Payments {

    private final String paymentOption;
    private final PaymentsImpl payments;

    public PaymentsImpl() {
        this.paymentOption = paymentOption;
    }

    public void addPaymentOption(String  paymentOption) {
        payments.setPaymentOption(paymentOption);
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
