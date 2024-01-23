package sorcer.arithmetic.provider;

import sorcer.arithmetic.provider.impl.MonitorData;
import sorcer.arithmetic.provider.impl.ShoppingList;
import sorcer.arithmetic.provider.impl.ShoppingSupplier;

import java.rmi.RemoteException;

public interface Payments {

    public void addPaymentOption(String paymentOption) throws RemoteException;

    public void pay() throws RemoteException;
}
