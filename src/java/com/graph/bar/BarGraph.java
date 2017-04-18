/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.bar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nail yusupov
 */
public class BarGraph {

    private List<String> labels;
    private List<BarItem> datasets;

    public BarGraph(){
        labels = new ArrayList<>();
        datasets = new ArrayList<>();
        datasets.add(new BarItem());
    }
    
    public void addBar(String d){
        getDatasets().get(0).addBar(d);
    }
    
    public void addLabel(String l){
        getLabels().add(l);
    }

    public void setChartLabel(String lab){
        datasets.get(0).setLabel(lab);
    }
    
    public void setBorderWidth(String w){
        datasets.get(0).setBorderWidth(w);
    }

    /**
     * @return the labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /**
     * @return the datasets
     */
    public List<BarItem> getDatasets() {
        return datasets;
    }

    /**
     * @param datasets the datasets to set
     */
    public void setDatasets(List<BarItem> datasets) {
        this.datasets = datasets;
    }
    
}
