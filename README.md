# TPG Wear

The **TPG Wear** app allows you to quickly get live information about the next departures of a given stop. It comes with a companion mobile app where you can store your favorite stops that will be displayed in the main menu of the smartwatch app.

The application uses the official **TPG** (_Geneva Public Transport_) API as source of data.

# Configuration

After you clone the repository you have to add the following lines to the file `gradle.properties`:

```ini
RELEASE_KEY_ALIAS      = ???
RELEASE_KEY_PASSWORD   = ???
RELEASE_STORE_PASSWORD = ???
RELEASE_STORE_FILE     = ???
```

Create a file in `common/src/main/res/xml/keys.xml` with the following content:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_key">APP_KEY</string>
</resources>
```


[![Get it on Google Play](http://mauriciotogneri.com/images/badge.png)](https://play.google.com/store/apps/details?id=com.mauriciotogneri.tpgwear)