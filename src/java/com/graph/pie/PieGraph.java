/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.pie;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nail yusupov
 */
public class PieGraph {

    private List<String> labels;
    private List<PieItem> datasets;

    public PieGraph() {
        labels = new ArrayList<>();
        datasets = new ArrayList<>();
        datasets.add(new PieItem());
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
    
    public void addSlice(String d){
        datasets.get(0).addSlice(d);
    }
    
    public void addLabel(String l){
        labels.add(l);
    }

    /**
     * @return the datasets
     */
    public List<PieItem> getDatasets() {
        return datasets;
    }

    /**
     * @param datasets the datasets to set
     */
    public void setDatasets(List<PieItem> datasets) {
        this.datasets = datasets;
    }

}
