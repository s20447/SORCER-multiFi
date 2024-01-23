package sorcer.arithmetic.provider.impl;

public class MonitorData {
    private int param1;
    private int param2;

    /**
     * More params to be created by monitoring process
     */
    public MonitorData() {
        this.param1 = 0;
        this.param2 = 0;
    }

    public int getParam1() {
        return param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam1(int monitorParam1) {
        if (monitorParam1 >= 0) {
            this.param1 = monitorParam1;
        }
    }

    public void setParam2(int monitorParam2) {
        if (monitorParam2 >= 0) {
            this.param2 = monitorParam2;
        }
    }
}
