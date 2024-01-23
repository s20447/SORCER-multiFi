package sorcer.arithmetic.provider.impl;

import sorcer.arithmetic.provider.impl.MonitorData;
import sorcer.arithmetic.provider.ui.IMonitor;


public class MonitorImpl implements IMonitor{

    private final int DAY_COUNT = 7;
    private final int CALORIE_TO_STEP_COEFF = 0.03;

    private float [] dailyHeartRate;
    private float [] dailyBloodPressure;
    private float [] dailyBloodSugar;
    private int [] dailySteps;
    private float [] nightlySleepDuration;

    public MonitorImpl() {
        dailyHeartRate = new float[DAY_COUNT];
        dailyBloodPressure = new float[DAY_COUNT];
        dailyBloodSugar = new float[DAY_COUNT];
        dailySteps = new int[DAY_COUNT];
        nightlySleepDuration = new float[DAY_COUNT];
    }

    private float getAverage(float[] arr) {
        float sum = 0;
        for (float x : arr) sum += x;
        return sum/arr.length;
    }

    private float stepsToCalories(int steps) {
        return steps * CALORIE_TO_STEP_COEFF;
    }

    public MonitorData getMonitorData() {
        float dailyCalories = new float[DAY_COUNT];
        for(int i = 0; i < dailySteps.length; i++) dailyCalories[i] = stepsToCalories(dailySteps[i]);
        return new MonitorData(getAverage(dailyHeartRate), getAverage(dailyBloodPressure), getAverage(dailyBloodSugar), getAverage(dailyCalories), getAverage(nightlySleepDuration));
    }
}