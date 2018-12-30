/*
 * Copyright 2001-2008 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spyhunter99.i18nl10n;

import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * This a resource loader for specific locales for internationalization
 *
 * @author AO
 */
public final class ResourceLoader {

    //web.properties, i18n_en_us.properties, i18n_es.properties, etc
    private static String BUNDLE_NAME = "i18n";

    private ResourceLoader() {
    }

    public static void setDefaultResourceBundlePackageName(String bundleName) {
        BUNDLE_NAME = bundleName;
    }

    public static Set<Locale> getSupportedLocales() {
        return getSupportedLocales(ResourceLoader.class);
    }

    public static Set<Locale> getSupportedLocales(Class resourceLoader) {
        Set<Locale> supportedLocales = new HashSet<Locale>();
        String packageName = BUNDLE_NAME;
        packageName = packageName.replace(".", "/");

        for (Locale l : Locale.getAvailableLocales()) {
            URL u = null;
            boolean isDefault = false;
            if (l.toString().equals("")) {
                u = resourceLoader.getResource("/" + packageName + ".properties");
                isDefault = true;
            } else {
                u = resourceLoader.getResource("/" + packageName + "_" + l.toString() + ".properties");
            }

            if (u != null) {
                if (isDefault) {
                    supportedLocales.add(Locale.getDefault());
                } else {
                    supportedLocales.add(l);
                }
            }
        }

        return supportedLocales;
    }

    public static Set<Locale> getSupportedLocales(String bundle) {
        Set<Locale> supportedLocales = new HashSet<Locale>();
        for (Locale l : Locale.getAvailableLocales()) {
            if (ResourceBundle.getBundle(bundle, l) != null) {
                supportedLocales.add(l);
            }
        }

        return supportedLocales;
    }

    public static Locale getLocale(String language, String country, String variant) {
        return new Locale(language, country == null ? "" : country, variant == null ? "" : variant);
    }

    /**
     * returns a localized string in the locale defined within locale or in the
     * default locale, en
     *
     * @param language
     * @param country
     * @param key
     * @return a localized string
     * @throws IllegalArgumentException if the key is null
     * @ if the resource bundle can't be found
     */
    public static String getResource(String language, String country, String key) {
        return getResource(getLocale(language, country, ""), key);
    }

    public static String getResource(String language, String country, String variant, String key) {
        return getResource(getLocale(language, country, variant), key);
    }

    public static String getResource(String language, String country, String variant, String bundle, String key) {
        return getResource(getLocale(language, country, variant), bundle, key);
    }

    public static String getResource(Locale locale, String key) {
        return getResource(locale, BUNDLE_NAME, key);
    }

    public static String getResource(Locale locale, String bundle, String key) {
        if (key == null) {
            throw new IllegalArgumentException("key");
        }

        ResourceBundle b = ResourceBundle.getBundle(bundle, locale);
        try {
            return b.getString(key.trim());
        } catch (Exception ex) {
            return "key " + key + " not found " + ex.getMessage();
        }
    }

    public static String formatDateTime(Locale locale, XMLGregorianCalendar input) {
        return formatDateTime(locale, BUNDLE_NAME, input.toGregorianCalendar().getTime());
    }

    public static String formatDateTime(Locale locale, Calendar input) {
        return formatDateTime(locale, BUNDLE_NAME, input.getTime());
    }

    public static String formatDateTime(Locale locale, XMLGregorianCalendar input, TimeZone zone) {
        return formatDateTime(locale, BUNDLE_NAME, input.toGregorianCalendar().getTime(), zone);
    }

    public static String formatDateTime(Locale locale, Calendar input, TimeZone zone) {
        return formatDateTime(locale, BUNDLE_NAME, input.getTime(), zone);
    }

    public static String formatDateTime(Locale locale, Date input) {
        return formatDateTime(locale, BUNDLE_NAME, input, TimeZone.getDefault());
    }

    public static String formatDateTime(Locale locale, String bundle, XMLGregorianCalendar input) {
        return formatDateTime(locale, bundle, input.toGregorianCalendar().getTime());
    }

    public static String formatDateTime(Locale locale, String bundle, Calendar input) {
        return formatDateTime(locale, bundle, input.getTime());
    }

    public static String formatDateTime(Locale locale, String bundle, XMLGregorianCalendar input, TimeZone zone) {
        return formatDateTime(locale, bundle, input.toGregorianCalendar().getTime(), zone);
    }

    public static String formatDateTime(Locale locale, String bundle, Calendar input, TimeZone zone) {
        return formatDateTime(locale, bundle, input.getTime(), zone);
    }

    public static String formatDateTime(Locale locale, String bundle, Date input) {
        return formatDateTime(locale, bundle, input, TimeZone.getDefault());
    }

    public static String formatDateTime(Locale locale, Date input, TimeZone zone) {
        return formatDateTime(locale, BUNDLE_NAME, input, zone);
    }

    public static String formatDateTime(Locale locale, String bundle, Date input, TimeZone zone) {
        String dtg = getResource(locale, bundle, "dateTimeFormat");
        if (dtg != null && !dtg.contains("not found")) {
            return formatDateTimeWithFormat(locale, dtg, input, zone);
        } else {
            //https://docs.oracle.com/javase/tutorial/i18n/format/dateFormat.html
            return DateFormat.getDateTimeInstance(
                    DateFormat.LONG,
                    DateFormat.LONG,
                    locale).format(input);
        }
    }

    public static String formatDateTimeWithFormat(Locale locale, String format, Date input, TimeZone zone) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        sdf.setTimeZone(zone);
        return sdf.format(input);
    }

    public static String formatCurrency(Locale locale, double input) {
        return NumberFormat.getCurrencyInstance(locale).format(input);
    }

    private static final String[] SI_UNITS = {"B", "kB", "MB", "GB", "TB", "PB", "EB"};
    private static final String[] BINARY_UNITS = {"B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB"};

    /**
     * https://stackoverflow.com/a/33753118/1203182
     *
     * @param bytes
     * @param useSIUnits
     * @param locale
     * @return
     */
    public static String formatBytes(final long bytes, final boolean useSIUnits, final Locale locale) {
        final String[] units = useSIUnits ? SI_UNITS : BINARY_UNITS;
        final int base = useSIUnits ? 1000 : 1024;

        // When using the smallest unit no decimal point is needed, because it's the exact number.
        if (bytes < base) {
            return bytes + " " + units[0];
        }

        final int exponent = (int) (Math.log(bytes) / Math.log(base));
        final String unit = units[exponent];
        return String.format(locale, "%.1f %s", bytes / Math.pow(base, exponent), unit);
    }
}
