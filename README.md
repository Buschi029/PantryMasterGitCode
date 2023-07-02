# PantryMasterGitCode

## Beschreibung
PantryMaster ist eine digitale Speisekammer-App, die es ermöglicht, Lebensmittel und Produkte einfach zu verwalten. Mit Hilfe eines Barcodescanners können Produkte eingescannt und der Speisekammer hinzufügt werden. Man erhält detaillierte Informationen zu den gescannten Produkten und kann je Produkt ein Ablaufdatum eingeben, um den Überblick über die Haltbarkeit der Lebensmittel zu behalten. Darüber hinaus bietet die App eine praktische Einkaufsliste, auf der Produkte hinzufügt werden können, um Einkäufe zu organisieren.

## Funktionen
1. Barcode-Scanner: Scanne den Barcode von Lebensmitteln und Produkten, um sie der digitalen Speisekammer hinzuzufügen.
2. Produktdetails: Erhalte detaillierte Informationen zu den gescannten Produkten, einschließlich Zutaten, Nährwertangaben und Allergenen.
3. Inventar: Behalte den Überblick über alle deinen Lebensmittel.
4. Ablaufdaten-Verwaltung: Trage die Ablaufdaten deiner Produkte ein, um einen Überblick über ihre Haltbarkeit zu haben und rechtzeitig auf ablaufende Produkte aufmerksam zu werden.
5. Einkaufsliste: Erstelle eine Einkaufsliste und füge Produkte hinzu, die du beim nächsten Einkauf benötigst.

## Starten des Backends
Um das Backend zu starten muss in der Kommandozeile oder PowerShell in den "Backend"-Ordner navigiert (PantryMasterGitCide/Backend) und der Befehl "docker-compose up --build" ausgeführt werden.

## Starten des Frontends
Um das Frontend im Emulator oder auf einem Android-Gerät starten zu können, muss im File "HttpRoutes.kt" (PantryMasterGitCode\Frontend\app\src\main\java\com\prime\pantrymastergitcode\api\HttpRoutes.kt) die BASE_URL zur eigenen IP-Adresse geändert werden. Dies ist notwendig, damit die HTTP Requests im Frontend getätigt werden können.
