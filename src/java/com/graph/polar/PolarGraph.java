/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.polar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nail yusupov
 */
public class PolarGraph {
    
    private List<PolarItem> datasets;
    private List<String> labels;
    
    public PolarGraph(){
        datasets = new ArrayList<>();
        datasets.add(new PolarItem());
        labels = new ArrayList<>();
    }
    
    public void addSlice(String data){
        getDatasets().get(0).addSlice(data);
    }
    
    public void addLabel(String lab){
        getLabels().add(lab);
    }

    /**
     * @return the datasets
     */
    public List<PolarItem> getDatasets() {
        return datasets;
    }

    /**
     * @param datasets the datasets to set
     */
    public void setDatasets(List<PolarItem> datasets) {
        this.datasets = datasets;
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
    
}
