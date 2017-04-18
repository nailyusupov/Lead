/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.polar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author nail yusupov
 */
public class PolarItem {
    
    private List<String> data;
    private List<String> backgroundColor;
    private String label;
    
    public PolarItem(){
        data = new ArrayList<>();
        backgroundColor = new ArrayList<>();
        label = "Chart Title";
    }
    
    void addSlice(String d){
        getData().add(d);
        getBackgroundColor().add("rgba(" + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + ",1)");
    }
    
    void setChartTitle(String s){
        setLabel(s);
    }

    /**
     * @return the data
     */
    public List<String> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<String> data) {
        this.data = data;
    }

    /**
     * @return the backgroundColor
     */
    public List<String> getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(List<String> backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    
    
}
