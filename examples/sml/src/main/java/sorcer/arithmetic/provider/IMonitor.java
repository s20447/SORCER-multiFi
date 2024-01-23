package sorcer.arithmetic.provider;

import sorcer.arithmetic.provider.impl.MonitorData;


import java.rmi.RemoteException;

public interface IMonitor {

    public MonitorData getMonitorData()  throws RemoteException;
}
