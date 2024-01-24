package sorcer.arithmetic.provider.impl;

import sorcer.arithmetic.provider.IFoodOrder;
import sorcer.arithmetic.provider.IMonitor;

import java.rmi.RemoteException;

/**
 * Implementing IMonitor interface enables this class to create personalised shopping list.
 */

public class FoodOrderImpl implements IFoodOrder, IMonitor {
    @Override
    public boolean createFoodPlan(MonitorData d) throws RemoteException {
        /**
         * Create a food plan based on monitoring service data.
         */
        return false;
    }

    @Override
    public boolean creteShoppingList() throws RemoteException {
        return false;
    }

    @Override
    public boolean chooseSupplier(ShoppingSupplier s) throws RemoteException {
        return false;
    }

    @Override
    public boolean orderItems(ShoppingList sl) throws RemoteException {
        return false;
    }

    @Override
    public MonitorData getMonitorData() throws RemoteException {
        /**
         * Get data from monitoring devices.
         */
        return null;
    }
}
