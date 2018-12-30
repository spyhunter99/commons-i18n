/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.spyhunter99.i18n.demo.web;

import com.github.spyhunter99.i18nl10n.ResourceLoader;
import com.github.spyhunter99.i18nl10n.ResourceLoaderWeb;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author AO
 */
public class AppInitializer implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ResourceLoaderWeb.setDefaultResourceBundlePackageName("i18n");
        ResourceLoader.setDefaultResourceBundlePackageName("i18n");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
