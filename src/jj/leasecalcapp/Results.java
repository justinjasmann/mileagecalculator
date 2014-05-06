package jj.leasecalcapp;

import org.joda.time.DateTime;

public class Results
{
    private DateTime purchaseDateTime;
    private DateTime currentDateTime;
    private int daysDelta;
    private int weeksDelta;
    private int monthsDelta;
    private int yearsDelta;
    private int targetMileage;

    public Results(DateTime purchaseDateTime, DateTime currentDateTime,
            int daysDelta, int weeksDelta, int monthsDelta, int yearsDelta, int targetMileage)
    {
        this.purchaseDateTime = purchaseDateTime;
        this.currentDateTime = currentDateTime;
        this.daysDelta = daysDelta;
        this.weeksDelta = weeksDelta;
        this.monthsDelta = monthsDelta;
        this.yearsDelta = yearsDelta;
        this.targetMileage = targetMileage;
    }

    public String getPurchaseDate()
    {
        return purchaseDateTime.toLocalDate().toString();
    }

    public String getCurrentDate()
    {
        return currentDateTime.toLocalDate().toString();
    }

    public int getDaysDelta()
    {
        return daysDelta;
    }

    public int getWeeksDelta()
    {
        return weeksDelta;
    }

    public int getMonthsDelta()
    {
        return monthsDelta;
    }

    public int getYearsDelta()
    {
        return yearsDelta;
    }

    public int getTargetMileage()
    {
        return targetMileage;
    }
}