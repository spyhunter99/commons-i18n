# commons-i18n

an attempt at building a generic support library for i18n and l10n support


## setup

`mvn clean install`

Then include in your `pom.xml` or gradle build script the maven coordinates for
JAR and WAR projects
	
	<groupId>com.github.spyhunter99</groupId>
    <artifactId>commons-i18nl10n</artifactId>
    <version>1.0.0-SNAPSHOT</version>
	
## setting up your resources

In your `src/main/resources` make a folder for all your I18N stuff. For demo, we'll use `i18n`.
Put a properties file in there named `resources.properties`

So the full path is `src/main/resources/i18n/resources.properties`.

Add a line in the file like this:
	
	key=Hello World

Make additon files for all locationizations that you want to support using the following pattern

`src/main/resources/i18n/resources_en_US.properties` where

 - `en`:ISO 639 alpha-2 or alpha-3 language code, or a language subtag up to 8 characters in length. 
 - `US` is the ISO 3166 alpha-2 country code or a UN M.49 numeric-3 area code.
 - A variant can also be used, adding `_` and the variant code.
 
## using i18n (non web apps)

Before using, set up the `ResourceLoader` class with the resource bundle defined above. In the demo, 
use `i18n.resources` using the following code:

	ResourceLoader.setDefaultResourceBundlePackageName("i18n.resources");

Anywhere you go to present some form a text to the user, call this.

	ResourceLoader.getResource(Local.getDefaultLocale(), "key");
	
This is useful for a standalone jar file that will always use the operating system's user defined language.

## usage i18n in a web environment.

Listing all available locales (where a resource bundle exists). This is useful for displaying a list of options
to the user for changinge languages or prefered languages.

	ResourceLoader.getSupportedLocales();

Detecting a locale from a browser request can be done with this

	ResourceLoaderWeb.detectLocale(request);
	
Detecting browser timezones

	Can't do it, sorry, give the user a drop down box to decide on their own.
	Instead, you can use Javascript to get the browser's current time zone, POST that
	the your web app, then set a session variable, then use that to translate to 
	Java's TimeZone class. This JSP generated javascript is then included as a script element
	
Once the user selects a language, use this to store the locale preference in the user's session object

	ResourceLoaderWeb.setLocale(session, locale);
	
Displaying text to the user
	
	ResourceLoaderWeb.getResource(session, "key");
	
ReourceLoaderWeb is basically a wrapper that handles for the HttpSession specific stuff.
	
	
## other usage scenarios

### I18N'ing Javascript

Sometimes in javascript, you have to do all kinds of strange stuff, which may include dynamically rewriting portions of a web page, converting
JSON to HTML, ajax error messages, etc. I18N'ing this can be a pain, your choices are:

1. Duplicate the logic of ResourceLoader in javascript and load all javascript based resource files in the browser. Effectively having 2 sets of resource files to maintain.
2. Use some JSP/Javascript trickery

The demo app uses option 2. See `src/main/webapp/js/i18n.js.jsp`. This takes the current user's selected locale, then loads all available javascript strings as Javascript constants.
Since it's loaded in the global scope, it's then availble to all javascript within the webapp. It's kind of a pain, but it makes adding new language support easier.


### DateTime

ResourceLoader and ResourceLoaderWeb can format date time based on a format you decide for your app using the resource key `dateTimeFormat`.

You can also format with a timezone. By default, it will render based on the system's time zone. This may not be ideal for web users. ResourceLoader has a 
function to either have you the developer define the formatting or to enable the user to define formatting.

	ResourceLoader.formatDateTimeWithFormat(locale, format, input, zone)



### Currency

	ResourceLoader.formatCurrency(locale, input);

### multiple bundles

The ResourceLoader class has many overloaded functions that accept additional parameters. check them out.



## TODO

- Support for multiple object formatting and for gender specific formats.

### FAQ

Q: Why are there references to Apache jUDDI everywhere?
A: This work is based on the lessions learned from building the jUDDI GUI. It has been simplified down to the basics for easier reuse.

Q: Why is there a ResourceLoader and ResourceLoaderWeb?
A: For a non-web applications, the servlet api does not need to be included and thus anything that references servlet apis was moved to a different class.