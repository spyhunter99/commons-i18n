/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.spyhunter99.i18nl10n;

import static com.github.spyhunter99.i18nl10n.ResourceLoader.getSupportedLocales;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author AO
 */
public class ResourceLoaderWeb {

    //web.properties, i18n_en_us.properties, i18n_es.properties, etc
    private static String BUNDLE_NAME = "i18n";

    private ResourceLoaderWeb() {
    }

    public static Locale setLocaleFromCookie(HttpSession session, String cookieValue) {

        String[] parts = cookieValue.split("\\_");

        Locale locale = null;
        if (parts.length == 1) {
            locale = new Locale(parts[0]);
        } else if (parts.length == 2) {
            locale = new Locale(parts[0], parts[1]);
        } else if (parts.length == 3) {
            locale = new Locale(parts[0], parts[1], parts[2]);
        } else {
            // ??? unknown?
            locale = Locale.getDefault();
        }
        session.setAttribute("locale", locale);
        return locale;
    }

    public static void setDefaultResourceBundlePackageName(String bundleName) {
        BUNDLE_NAME = bundleName;
    }

    public static boolean isLocaleSet(HttpSession session) {
        return session.getAttribute("locale") != null;
    }

    public static Locale detectLocale(HttpServletRequest request) {
        Enumeration locales = request.getLocales();
        Set<Locale> supported = getSupportedLocales(BUNDLE_NAME);
        /**
         * You can get the first one matches your supported local like below
         */
        while (locales.hasMoreElements()) {
            Locale locale = (Locale) locales.nextElement();
            if (supported.contains(locale)) {
                return locale;
            }
        }
        return Locale.getDefault();
    }

    public static void setLocale(HttpSession session, Locale locale) {
        session.setAttribute("locale", locale);
    }

    /**
     * returns a localized string in the locale defined within
     * session.getAttribute("locale") or in the default locale, en
     *
     * @param session
     * @param key
     * @return a localized string
     * @throws IllegalArgumentException if the key is null
     * @ if the resource bundle can't be found
     */
    public static String getResource(HttpSession session, String key) {
        return getResource(session, BUNDLE_NAME, key);
    }

    public static String getResource(HttpSession session, String bundle, String key) {
        if (key == null) {
            throw new IllegalArgumentException("key");
        }
        Locale locale = getLocale(session);
        return ResourceLoader.getResource(locale, key);
    }

    public static Locale getLocale(HttpSession session) {
        Locale locale = null;
        if (session != null) {
            locale = (Locale) session.getAttribute("locale");
        }
        if (locale == null) {
            locale = new Locale("en", "US");
        }
        return locale;
    }

    public static String formatDateTime(HttpSession session, XMLGregorianCalendar input) {
        return ResourceLoader.formatDateTime(getLocale(session), input);
    }

    public static String formatDateTime(HttpSession session, Calendar input) {
        return ResourceLoader.formatDateTime(getLocale(session), input.getTime());
    }

    public static String formatDateTime(HttpSession session, XMLGregorianCalendar input, TimeZone zone) {
        return ResourceLoader.formatDateTime(getLocale(session), input, zone);
    }

    public static String formatDateTime(HttpSession session, Calendar input, TimeZone zone) {
        return ResourceLoader.formatDateTime(getLocale(session), input, zone);
    }

    public static String formatDateTime(HttpSession session, Date input, TimeZone zone) {
        return ResourceLoader.formatDateTime(getLocale(session), input, zone);
    }

    public static String formatDateTime(HttpSession session, Date input) {
        return ResourceLoader.formatDateTime(getLocale(session), input);
    }

    public static String formatCurrency(HttpSession session, double input) {
        return ResourceLoader.formatCurrency(getLocale(session), input);
    }
}
