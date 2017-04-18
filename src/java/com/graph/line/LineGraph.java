/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.line;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nail yusupov
 */
public class LineGraph {

    private List<String> labels;
    private List<LineItem> datasets;

    public LineGraph() {
        labels = new ArrayList<>();
        datasets = new ArrayList<>();
    }
    
    public void addLineBreak(String lineBreak){
        labels.add(lineBreak);
    }
    
    public void addLabel(String lable){
        labels.add(lable);
    }
    
    public void addLine(LineItem itme){
        datasets.add(itme);
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
    public List<LineItem> getDatasets() {
        return datasets;
    }

    /**
     * @param datasets the datasets to set
     */
    public void setDatasets(List<LineItem> datasets) {
        this.datasets = datasets;
    }

}
