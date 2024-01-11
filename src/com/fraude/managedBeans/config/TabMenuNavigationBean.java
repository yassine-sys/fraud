package com.fraude.managedBeans.config;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.extensions.model.layout.LayoutOptions;

@ManagedBean
@SessionScoped
public class TabMenuNavigationBean implements Serializable {

	/**
	 * 
	 */

	private LayoutOptions layoutOptions;

	public LayoutOptions getLayoutOptions() {
		return layoutOptions;
	}

	public void setLayoutOptions(LayoutOptions layoutOptions) {
		this.layoutOptions = layoutOptions;
	}

	private static final long serialVersionUID = 1L;
	private Long activeIndex;

	private boolean naviguer;

	public boolean isNaviguer() {
		return naviguer;
	}

	public void setNaviguer(boolean naviguer) {
		this.naviguer = naviguer;
	}

	public Long getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(Long activeIndex) {
		this.activeIndex = activeIndex;
	}

	@PostConstruct
	public void init() {
		activeIndex = (long) 0;
		layoutOptions = new LayoutOptions();

		// options for all panes
		LayoutOptions panes = new LayoutOptions();
		panes.addOption("slidable", false);
		layoutOptions.setPanesOptions(panes);

		// north pane
		LayoutOptions north = new LayoutOptions();
		north.addOption("resizable", false);
		north.addOption("closable", false);
		north.addOption("size", 60);
		north.addOption("spacing_open", 0);
		layoutOptions.setNorthOptions(north);

		// south pane
		LayoutOptions south = new LayoutOptions();
		south.addOption("resizable", false);
		south.addOption("closable", false);
		south.addOption("size", 28);
		south.addOption("spacing_open", 0);
		layoutOptions.setSouthOptions(south);

		// west pane
		LayoutOptions west = new LayoutOptions();
		west.addOption("size", 340);
		west.addOption("minSize", 150);
		west.addOption("maxSize", 500);
		layoutOptions.setWestOptions(west);

		LayoutOptions center = new LayoutOptions();
		center.addOption("resizable", false);
		center.addOption("closable", false);
		center.addOption("minWidth", 200);
		center.addOption("minHeight", 60);
		layoutOptions.setCenterOptions(center);

		// set options for nested center layout
		LayoutOptions optionsNested = new LayoutOptions();
		center.setChildOptions(optionsNested);

		// options for center-north pane
		LayoutOptions centerEast = new LayoutOptions();
		centerEast.addOption("size", "20%");
		optionsNested.setEastOptions(centerEast);

		// options for center-center pane
		LayoutOptions centerCenter = new LayoutOptions();
		centerCenter.addOption("minHeight", 60);
		optionsNested.setCenterOptions(centerCenter);

	}

	private boolean header_navigation = false;

	public boolean isHeader_navigation() {
		return header_navigation;
	}

	public void setHeader_navigation(boolean header_navigation) {
		this.header_navigation = header_navigation;
	}

	private boolean pan_admin = false;
	private boolean pan_reporting = false;
	private boolean pan_postpaye = false;
	private boolean pan_interco = false;
	private boolean pan_fraude = false;
	private boolean pan_roa_in = false;
	private boolean pan_intercoNat = false;
	private boolean pan_roam_out = false;
	private boolean pan_smart_closing = false;
	private boolean pan_dg = false;

	public boolean isPan_dg() {
		return pan_dg;
	}

	public void setPan_dg(boolean pan_dg) {
		this.pan_dg = pan_dg;
	}

	public boolean isPan_smart_closing() {
		return pan_smart_closing;
	}

	public void setPan_smart_closing(boolean pan_smart_closing) {
		this.pan_smart_closing = pan_smart_closing;
	}

	public boolean isPan_roam_out() {
		return pan_roam_out;
	}

	public void setPan_roam_out(boolean pan_roam_out) {
		this.pan_roam_out = pan_roam_out;
	}

	public boolean isPan_intercoNat() {
		return pan_intercoNat;
	}

	public void setPan_intercoNat(boolean pan_intercoNat) {
		this.pan_intercoNat = pan_intercoNat;
	}

	public boolean isPan_roa_in() {
		return pan_roa_in;
	}

	public void setPan_roa_in(boolean pan_roa_in) {
		this.pan_roa_in = pan_roa_in;
	}

	public boolean isPan_postpaye() {
		return pan_postpaye;
	}

	public void setPan_postpaye(boolean pan_postpaye) {
		this.pan_postpaye = pan_postpaye;
	}

	public boolean isPan_admin() {
		return pan_admin;
	}

	public boolean isPan_reporting() {
		return pan_reporting;
	}

	public void setPan_admin(boolean pan_admin) {
		this.pan_admin = pan_admin;
	}

	public void setPan_reporting(boolean pan_reporting) {
		this.pan_reporting = pan_reporting;
	}

	private boolean pan_prepaye=false;

	public boolean ispan_prepaye()
	{
		return pan_prepaye;
	}
	public void setpan_prepaye(
	boolean pan_prepaye)
	{
		this.pan_prepaye = pan_prepaye;
	}

	public boolean isPan_interco() {
		return pan_interco;
	}

	public void setPan_interco(boolean pan_interco) {
		this.pan_interco = pan_interco;
	}

	public void changeActiveIndex(String x,long index){
		
		
		header_navigation=false;
		if(x.equals("Administration")){
			pan_prepaye=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_admin=true;
			pan_interco=false;
			pan_fraude=false;
			pan_roa_in= false;
			pan_intercoNat= false;
			pan_roam_out = false;
			pan_smart_closing=false;
			pan_dg=false;

			activeIndex=index;
	
			
		}
if(x.equals("Mobile Pr�pay�")){
	header_navigation=false;
	pan_prepaye=true;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_interco=false;
			pan_fraude=false;
			pan_roa_in= false;
			pan_intercoNat= false;
			pan_roam_out = false;
			pan_smart_closing=false;
			pan_dg=false;

			activeIndex=index;
	
		}
		if(x.equals("Mobile Postpay�")){
			header_navigation=false;
			pan_prepaye=false;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=true;
			pan_interco=false;
			pan_fraude=false;
			pan_roa_in=false;
			pan_intercoNat= false;
			pan_roam_out = false;
			pan_smart_closing=false;
			pan_dg=false;

			activeIndex=index;
	
		}
		if(x.equals("Interco International")){
			header_navigation=false;
			pan_prepaye=false;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_interco=true;
			pan_fraude=false;
			pan_roa_in=false;
			pan_intercoNat= false;
			pan_roam_out = false;
			pan_smart_closing=false;
			pan_dg=false;

			activeIndex=index;
		}
		if(x.equals("Interco National")){
			header_navigation=false;
			pan_prepaye=false;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_interco=false;
			pan_fraude=false;
			pan_roa_in=false;
			pan_intercoNat= true;
			pan_roam_out = false;
			pan_smart_closing=false;
			pan_dg=false;

			activeIndex=index;
		}
		if(x.equals("Roaming IN")){
			pan_prepaye=false;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_interco=false;
			pan_fraude=false;
			pan_roa_in=true;
			pan_intercoNat= false;
			pan_roam_out = false;
			pan_smart_closing=false;
			pan_dg=false;

			activeIndex=index;
		}
		if(x.equals("Roaming OUT")){
			header_navigation=false;
			pan_prepaye=false;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_interco=false;
			pan_fraude=false;
			pan_roa_in=false;
			pan_intercoNat= false;
			pan_roam_out = true;
			pan_smart_closing=false;
			pan_dg=false;

			activeIndex=index;
	
		}
		if(x.equals("Fraude")){
			header_navigation=false;
			pan_prepaye=false;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_interco=false;
			pan_fraude=true;
			pan_roa_in=false;
			pan_roam_out=false;
			pan_intercoNat= false;
			pan_smart_closing=false;
			pan_dg=false;

			activeIndex=index;
		}
		if(x.equals("SMART Closing")){
			header_navigation=false;
			pan_prepaye=false;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_interco=false;
			pan_fraude=false;
			pan_roa_in=false;
			pan_roam_out=false;
			pan_intercoNat= false;
			pan_smart_closing=true;
			pan_dg=false;

			activeIndex=index;
		}
		if(x.equals("DG")){
			header_navigation=false;
			pan_prepaye=false;
			pan_admin=false;
			pan_reporting=false;
			pan_postpaye=false;
			pan_interco=false;
			pan_fraude=false;
			pan_roa_in=false;
			pan_roam_out=false;
			pan_intercoNat= false;
			pan_smart_closing=false;
			pan_dg=true;
			activeIndex=index;
		}
		
		
		
				
			
			
		
	}

	public boolean isPan_fraude() {
		return pan_fraude;
	}

	public void setPan_fraude(boolean pan_fraude) {
		this.pan_fraude = pan_fraude;
	}

}
