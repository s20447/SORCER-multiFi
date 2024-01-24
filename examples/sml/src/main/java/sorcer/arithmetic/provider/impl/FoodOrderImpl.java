package sorcer.arithmetic.provider.impl;

import sorcer.arithmetic.provider.IFoodOrder;

import java.rmi.RemoteException;

/**
 * Implementing IMonitor interface enables this class to create personalised shopping list.
 */

public class FoodOrderImpl implements IFoodOrder{
    @Override
    public String[] createFoodPlan(MonitorData d) throws RemoteException {
        /*
          Create a food plan based on monitoring service data.
         */
        return new String[1];
    }

    @Override
    public ShoppingList creteShoppingList(String[] foodPlan) throws RemoteException {
        return new ShoppingList();
    }

    @Override
    public float orderItems(ShoppingList sl) throws RemoteException {
        /*
          Just for further exertion purposes, function returns amount to pay.
         */
        return 0.0f;
    }
}
