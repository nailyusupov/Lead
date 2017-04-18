/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.pie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author nail yusupov
 */
class PieItem {
    
    private List<String> data;
    private List<String> backgroundColor;
    
    public PieItem(){
       data = new ArrayList<>();
        backgroundColor = new ArrayList<>();
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
    
    public void addSlice(String d){
        data.add(d);
        backgroundColor.add("rgba(" + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + ",1)");
    }

}
