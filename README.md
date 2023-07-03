# PantryMasterGitCode

## Beschreibung
PantryMaster ist eine digitale Speisekammer-App, die es ermöglicht, Lebensmittel und Produkte einfach zu verwalten. Mit Hilfe eines Barcodescanners können Produkte
eingescannt und der Speisekammer hinzufügt werden. Man erhält detaillierte Informationen zu den gescannten Produkten und kann je Produkt ein Ablaufdatum eingeben,
um den Überblick über die Haltbarkeit der Lebensmittel zu behalten. Darüber hinaus bietet die App eine praktische Einkaufsliste, auf der Produkte hinzufügt werden können,
um Einkäufe zu organisieren.

## Funktionen
1. Barcode-Scanner: Scanne den Barcode von Lebensmitteln und Produkten, um sie der digitalen Speisekammer hinzuzufügen.
2. Produktdetails: Erhalte detaillierte Informationen zu den gescannten Produkten, einschließlich Zutaten, Nährwertangaben und Allergenen.
3. Inventar: Behalte den Überblick über alle deinen Lebensmittel.
4. Ablaufdaten-Verwaltung: Trage die Ablaufdaten deiner Produkte ein, um einen Überblick über ihre Haltbarkeit zu haben und rechtzeitig auf ablaufende Produkte
5. aufmerksam zu werden.
5. Einkaufsliste: Erstelle eine Einkaufsliste und füge Produkte hinzu, die du beim nächsten Einkauf benötigst.

## Starten des Backends
Um das Backend zu starten muss in der Kommandozeile oder PowerShell in den "Backend"-Ordner navigiert (PantryMasterGitCode/Backend) und der Befehl "docker-compose up
--build" ausgeführt werden.

## Starten des Frontends
Um die bereits kompillierte APK zu starten, muss diese auf einem Emulator installiert werden, dabei muss der Computer der den Emulator hostet auch das Backend hosten.
Soll die App auf einem externen Android-Gerät oder in einem Emulator gestartet werden, auf dessen Computer nicht das Backend gehostet wird, muss zuerst die URL zum
Backend angepasst werden.
Dazu muss im File "HttpRoutes.kt" (PantryMasterGitCode\Frontend\app\src\main\java\com\prime\pantrymastergitcode\api\HttpRoutes.kt) die BASE_URL zur IP-Adresse des
Computers welcher das Backend hostet, geändert werden. Zusätzlich müssen sich die Geräte im selben WLAN-Netz befinden.
Sollte eine Änderung der BASE_URL erfolgt sein, muss die App vor Ausführung neu kompiliert werden. Dazu kann Android Studio genutzt werden. Um die App neu zu
kompilieren muss der Ordner "PantryMasterGitCode\Frontend" in Android Studio geöffet werden. Nach Auswahl des gewünschten Gerätes oder Emulators (oben rechts) auf dem
die App installiert werden soll, wird durch betätigen des Run-Knopfes (neben der Geräte-Auswahl) die App kompiliert und auf dem ausgewählten Gerät installiert.
Damit der Funktionalitätsumfang der App gewährleistet werden kann, muss der Emulator/das Android Gerät Google Play Services installiert haben.
