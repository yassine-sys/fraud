package com.web.service;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fraude.entities.CorsFilter;
import com.fraude.managedBeans.config.CategoriesFraudeMbean;
import com.fraude.managedBeans.config.FraudeMbean;
import com.fraude.managedBeans.config.GestionPositionsMbean;
import com.fraude.managedBeans.config.HotListMbean;
import com.fraude.managedBeans.warnings.AlertesFraude;
import com.fraude.managedBeans.warnings.StatFraudeMbean;

@ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
		resources.add(CorsFilter.class);
        addRestResourceClasses(resources);

        return resources;
    }
    private void addRestResourceClasses(Set<Class<?>> resources) {
        //add all resources classes
        resources.add(CategoriesFraudeMbean.class);
        resources.add(GestionPositionsMbean.class);
        resources.add(HotListMbean.class);
        resources.add(FraudeMbean.class);
		resources.add(StatFraudeMbean.class);
		resources.add(AlertesFraude.class);

       
    }
}