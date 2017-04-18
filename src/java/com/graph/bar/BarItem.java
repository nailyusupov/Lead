/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.bar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author nail yusupov
 */
public class BarItem {

    private List<String> data;
    private List<String> backgroundColor;
    private List<String> borderColor;
        private String label;
    private String borderWidth;

    public BarItem() {
        label = "Chart Label";
        borderWidth = "1";
        data = new ArrayList<>();
        backgroundColor = new ArrayList<>();
        borderColor = new ArrayList<>();
    }
    
    void addBar(String d){
        getData().add(d);
        String tempColor = "rgba(" + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + ",";
        getBackgroundColor().add(tempColor+"0.2)");
        getBorderColor().add(tempColor+"1)");
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
     * @return the borderColor
     */
    public List<String> getBorderColor() {
        return borderColor;
    }

    /**
     * @param borderColor the borderColor to set
     */
    public void setBorderColor(List<String> borderColor) {
        this.borderColor = borderColor;
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

    /**
     * @return the borderWidth
     */
    public String getBorderWidth() {
        return borderWidth;
    }

    /**
     * @param borderWidth the borderWidth to set
     */
    public void setBorderWidth(String borderWidth) {
        this.borderWidth = borderWidth;
    }

}
