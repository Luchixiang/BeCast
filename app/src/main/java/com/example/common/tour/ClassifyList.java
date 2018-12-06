package com.example.common.tour;

import java.util.ArrayList;
import java.util.List;

public class ClassifyList {
    private int requestCount;
    private ArrayList<Classify> classifies = new ArrayList<>();

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }


    public ArrayList<Classify> getClassifies() {
        return classifies;
    }

    public void setClassifies(ArrayList<Classify> classifies) {
        this.classifies = classifies;
    }
}
