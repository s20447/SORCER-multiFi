package sorcer.arithmetic.provider.impl;

public class MonitorData {
    private float heartRate;
    private float bloodPressure;
    private float bloodSugar;
    private float caloriesBurned;
    private float sleepDuration;

    public MonitorData(float heartRate, float bloodPressure, float bloodSugar, float caloriesBurned, float sleepDuration) {
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.bloodSugar = bloodSugar;
        this.caloriesBurned = caloriesBurned;
        this.sleepDuration = sleepDuration;
    }

    public float getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(float heartRate) {
        if(heartRate >= 0) {
            this.heartRate = heartRate;
        }
    }

    public float getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(float bloodPressure) {
        if(bloodPressure >= 0) {
            this.bloodPressure = bloodPressure;
        }
    }

    public float getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(float bloodSugar) {
        if(bloodSugar >= 0) {
            this.bloodSugar = bloodSugar;
        }
    }

    public float getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(float caloriesBurned) {
        if(caloriesBurned >= 0) {
            this.caloriesBurned = caloriesBurned;
        }
    }

    public float getSleepDuration() {
        return sleepDuration;
    }

    public void setSleepDuration(float sleepDuration) {
        if(sleepDuration >= 0) {
            this.sleepDuration = sleepDuration;
        }
    }
}
