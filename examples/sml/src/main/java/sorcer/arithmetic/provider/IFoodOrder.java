package sorcer.arithmetic.provider;

import sorcer.arithmetic.provider.impl.MonitorData;
import sorcer.arithmetic.provider.impl.ShoppingList;
import sorcer.arithmetic.provider.impl.ShoppingSupplier;

import java.rmi.RemoteException;

public interface IFoodOrder {

    public String[] createFoodPlan(MonitorData d) throws RemoteException;

    public ShoppingList creteShoppingList(String[] foodPlan) throws RemoteException;

    public float orderItems(ShoppingList sl) throws RemoteException;
}
