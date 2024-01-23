package sorcer.arithmetic.provider;

import sorcer.arithmetic.provider.impl.MonitorData;
import sorcer.arithmetic.provider.impl.ShoppingList;
import sorcer.arithmetic.provider.impl.ShoppingSupplier;

import java.rmi.RemoteException;

public interface IFoodOrder {

    public boolean createFoodPlan(MonitorData d) throws RemoteException;

    public boolean creteShoppingList() throws RemoteException;

    public boolean chooseSupplier(ShoppingSupplier s) throws RemoteException;

    public boolean orderItems(ShoppingList sl) throws RemoteException;
}
