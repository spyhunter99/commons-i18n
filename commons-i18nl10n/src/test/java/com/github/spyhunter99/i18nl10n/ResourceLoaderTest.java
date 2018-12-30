/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.i18nl10n;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author AO
 */
public class ResourceLoaderTest {

    @BeforeClass
    public static void setUpClass() {
        ResourceLoader.setDefaultResourceBundlePackageName("test1.i18n");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testGetResource() throws Exception {
        String msg = ResourceLoader.getResource(Locale.ENGLISH, "title");
        Assert.assertNotNull(msg);
        Assert.assertEquals("Hello World", msg);
    }

    @Test
    public void testGetResource2() throws Exception {
        String msg = ResourceLoader.getResource(ResourceLoader.getLocale("en", "US", null), "title");
        Assert.assertNotNull(msg);
        Assert.assertEquals("Hello World", msg);
    }
    
    @Test
    public void testGetResource3() throws Exception {
        String msg = ResourceLoader.getResource(ResourceLoader.getLocale("en", null, null), "title");
        Assert.assertNotNull(msg);
        Assert.assertEquals("Hello World", msg);
    }

    @Test
    public void getSupportedLocales() throws Exception {
        Set<Locale> locales = ResourceLoader.getSupportedLocales(ResourceLoaderTest.class);
        Assert.assertNotNull(locales);
        Assert.assertEquals(2, locales.size());
    }
    @Test
    public void testGetMissingResource() throws Exception {
        Assert.assertTrue(ResourceLoader.getResource(Locale.ENGLISH, UUID.randomUUID().toString()).contains("not found"));

    }

}
