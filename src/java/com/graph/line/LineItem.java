/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.line;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author nail yusupov
 */

public class LineItem {

    private String label, backgroundColor, borderColor, borderCapStyle, borderJoinStyle, pointBorderColor, pointBackgroundColor, pointHoverBackgroundColor, pointHoverBorderColor;
    private boolean fill, spanGaps;
    private List<String> data;
    private double lineTension;
    private int pointBorderWidth, pointHoverRadius, pointHoverBorderWidth, pointRadius, pointHitRadius;
    //private String borderDash, borderDashOffset;

    public LineItem() {
        label = "";
        fill = false;
        lineTension = 0;
        backgroundColor = "";
        borderColor = "";
        borderCapStyle = "butt";
        borderJoinStyle = "miter";
        pointBorderColor = "";
        pointBackgroundColor = "#fff";
        pointBorderWidth = 1;
        pointHoverRadius = 5;
        pointHoverBackgroundColor = "";
        pointHoverBorderColor = "rgba(220,220,220,1)";
        pointHoverBorderWidth = 2;
        pointRadius = 1;
        pointHitRadius = 10;
        data = new ArrayList<String>();
        spanGaps = false;
        setColor("rgba(" + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + ",1)");
    }

    public LineItem(String name) {
        label = name;
        fill = false;
        lineTension = 0;
        backgroundColor = "";
        borderColor = "";
        borderCapStyle = "butt";
        borderJoinStyle = "miter";
        pointBorderColor = "";
        pointBackgroundColor = "#fff";
        pointBorderWidth = 1;
        pointHoverRadius = 5;
        pointHoverBackgroundColor = "";
        pointHoverBorderColor = "rgba(220,220,220,1)";
        pointHoverBorderWidth = 2;
        pointRadius = 1;
        pointHitRadius = 10;
        data = new ArrayList<String>();
        spanGaps = false;
        setColor("rgba(" + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + "," + String.valueOf(ThreadLocalRandom.current().nextInt(1, 254 + 1)) + ",1)");
    }
    
    public void addData(String dataPiece){
        data.add(dataPiece);
    }

    public void setName(String name) {
        label = name;
    }

    public void setColor(String color) {
        backgroundColor = color;
        borderColor = color;
        pointBorderColor = color;
        pointHoverBackgroundColor = color;
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
     * @return the backgroundColor
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the borderColor
     */
    public String getBorderColor() {
        return borderColor;
    }

    /**
     * @param borderColor the borderColor to set
     */
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * @return the borderCapStyle
     */
    public String getBorderCapStyle() {
        return borderCapStyle;
    }

    /**
     * @param borderCapStyle the borderCapStyle to set
     */
    public void setBorderCapStyle(String borderCapStyle) {
        this.borderCapStyle = borderCapStyle;
    }

    /**
     * @return the borderJoinStyle
     */
    public String getBorderJoinStyle() {
        return borderJoinStyle;
    }

    /**
     * @param borderJoinStyle the borderJoinStyle to set
     */
    public void setBorderJoinStyle(String borderJoinStyle) {
        this.borderJoinStyle = borderJoinStyle;
    }

    /**
     * @return the pointBorderColor
     */
    public String getPointBorderColor() {
        return pointBorderColor;
    }

    /**
     * @param pointBorderColor the pointBorderColor to set
     */
    public void setPointBorderColor(String pointBorderColor) {
        this.pointBorderColor = pointBorderColor;
    }

    /**
     * @return the pointBackgroundColor
     */
    public String getPointBackgroundColor() {
        return pointBackgroundColor;
    }

    /**
     * @param pointBackgroundColor the pointBackgroundColor to set
     */
    public void setPointBackgroundColor(String pointBackgroundColor) {
        this.pointBackgroundColor = pointBackgroundColor;
    }

    /**
     * @return the pointHoverBackgroundColor
     */
    public String getPointHoverBackgroundColor() {
        return pointHoverBackgroundColor;
    }

    /**
     * @param pointHoverBackgroundColor the pointHoverBackgroundColor to set
     */
    public void setPointHoverBackgroundColor(String pointHoverBackgroundColor) {
        this.pointHoverBackgroundColor = pointHoverBackgroundColor;
    }

    /**
     * @return the pointHoverBorderColor
     */
    public String getPointHoverBorderColor() {
        return pointHoverBorderColor;
    }

    /**
     * @param pointHoverBorderColor the pointHoverBorderColor to set
     */
    public void setPointHoverBorderColor(String pointHoverBorderColor) {
        this.pointHoverBorderColor = pointHoverBorderColor;
    }

    /**
     * @return the fill
     */
    public boolean isFill() {
        return fill;
    }

    /**
     * @param fill the fill to set
     */
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    /**
     * @return the spanGaps
     */
    public boolean isSpanGaps() {
        return spanGaps;
    }

    /**
     * @param spanGaps the spanGaps to set
     */
    public void setSpanGaps(boolean spanGaps) {
        this.spanGaps = spanGaps;
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
     * @return the lineTension
     */
    public double getLineTension() {
        return lineTension;
    }

    /**
     * @param lineTension the lineTension to set
     */
    public void setLineTension(double lineTension) {
        this.lineTension = lineTension;
    }

    /**
     * @return the pointBorderWidth
     */
    public int getPointBorderWidth() {
        return pointBorderWidth;
    }

    /**
     * @param pointBorderWidth the pointBorderWidth to set
     */
    public void setPointBorderWidth(int pointBorderWidth) {
        this.pointBorderWidth = pointBorderWidth;
    }

    /**
     * @return the pointHoverRadius
     */
    public int getPointHoverRadius() {
        return pointHoverRadius;
    }

    /**
     * @param pointHoverRadius the pointHoverRadius to set
     */
    public void setPointHoverRadius(int pointHoverRadius) {
        this.pointHoverRadius = pointHoverRadius;
    }

    /**
     * @return the pointHoverBorderWidth
     */
    public int getPointHoverBorderWidth() {
        return pointHoverBorderWidth;
    }

    /**
     * @param pointHoverBorderWidth the pointHoverBorderWidth to set
     */
    public void setPointHoverBorderWidth(int pointHoverBorderWidth) {
        this.pointHoverBorderWidth = pointHoverBorderWidth;
    }

    /**
     * @return the pointRadius
     */
    public int getPointRadius() {
        return pointRadius;
    }

    /**
     * @param pointRadius the pointRadius to set
     */
    public void setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
    }

    /**
     * @return the pointHitRadius
     */
    public int getPointHitRadius() {
        return pointHitRadius;
    }

    /**
     * @param pointHitRadius the pointHitRadius to set
     */
    public void setPointHitRadius(int pointHitRadius) {
        this.pointHitRadius = pointHitRadius;
    }

}

