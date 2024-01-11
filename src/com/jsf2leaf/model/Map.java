/*
A JSF2 Leaflet wrapper component for OpenStreetMap
Copyright (C) 2015 Leonardo Ciocari

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.*/

package com.jsf2leaf.model;

import java.util.ArrayList;
import java.util.List;

public class Map {

	List<Layer> layers = new ArrayList<Layer>();
	private LatLong center;
	private String width = "300px";
	private String height = "200px";
	private String attribution = "Map data &copy; <a href=\"http://openstreetmap.org\">OpenStreetMap</a> contributors,<a href=\"http://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>";
	private int zoom = 1;
	private int minZoom = 1;
	private int maxZoom = 19;
	private boolean zoomControl = true;
	private boolean zoomEnabled = true;	
	private boolean draggingEnabled = true;
	private boolean layerControl = true;

	public List<Layer> getLayers() {
		return layers;
	}

	public Map addLayer(List<Layer> layers) {
		this.layers.addAll(layers);
		return this;
	}

	public Map addLayer(Layer layer) {
		this.layers.add(layer);
		return this;
	}

	public boolean isLayerControl() {
		return layerControl;
	}

	/**
	 * Set if the Map will have the layers checkbox control panel
	 */
	public Map setLayerControl(boolean layerControl) {
		this.layerControl = layerControl;
		return this;
	}

	public String getWidth() {
		return width;
	}

	public Map setWidth(String width) {
		this.width = width;
		return this;
	}

	public String getHeight() {
		return height;
	}

	public Map setHeight(String height) {
		this.height = height;
		return this;
	}

	public LatLong getCenter() {
		return center;
	}

	/**
	 * Set the Map start position
	 */
	public Map setCenter(LatLong center) {
		this.center = center;
		return this;
	}

	public String getAttribution() {
		return attribution;
	}

	/**
	 * Set the Map attribution/credits
	 */
	public Map setAttribution(String attribution) {
		this.attribution = attribution;
		return this;
	}

	public int getZoom() {
		return zoom;
	}

	/**
	 * Set the initial Map zoom
	 */
	public Map setZoom(int zoom) {
		this.zoom = zoom;
		return this;
	}

	public int getMinZoom() {
		return minZoom;
	}

	/**
	 * Set the minimum allowed zoom of the Map 
	 */
	public Map setMinZoom(int minZoom) {
		this.minZoom = minZoom;
		return this;
	}

	public int getMaxZoom() {
		return maxZoom;
	}

	/**
	 * Set the maximum allowed zoom of the Map 
	 */
	public Map setMaxZoom(int maxZoom) {
		this.maxZoom = maxZoom;
		return this;
	}

	public boolean isZoomControl() {
		return zoomControl;
	}

	/**
	 * Set if the Map will have the zoom buttons 
	 */
	public Map setZoomControl(boolean zoomControl) {
		this.zoomControl = zoomControl;
		return this;
	}

	public boolean isZoomEnabled() {
		return zoomEnabled;
	}

	/**
	 * Set if zoom will be available on the Map 
	 */
	public Map setZoomEnabled(boolean zoomEnabled) {
		this.zoomEnabled = zoomEnabled;
		return this;
	}

	public boolean isDraggingEnabled() {
		return draggingEnabled;
	}

	/**
	 * Set if the Map will be allowed do move 
	 */
	public Map setDraggingEnabled(boolean draggingEnabled) {
		this.draggingEnabled = draggingEnabled;
		return this;
	}

	@Override
	public String toString() {
		return "Map [layers=" + layers.toString() + ", center=" + center + ", width="
				+ width + ", height=" + height + ", attribution=" + attribution
				+ ", zoom=" + zoom + ", minZoom=" + minZoom + ", maxZoom="
				+ maxZoom + ", zoomControl=" + zoomControl + ", zoomEnabled="
				+ zoomEnabled + ", dragging=" + draggingEnabled + ", layerControl="
				+ layerControl + "]";
	}

}
