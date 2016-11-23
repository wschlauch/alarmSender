AlarmSender ist für das Senden von HL7 Nachrichten an den Port 8000 zuständig um eine PIIC/iX zu simulieren; aktuell sendet es die Daten aus src/data/alarmexamplesv2.txt.

Zum kompilieren: "mvn clean package" - damit wird eine neue JAR-Datei erstellt, die alle nötigen Vorraussetzungen gleich mit einpackt
Zum Starten: "java -jar target/alarmRedux-0.0.1-SNAPSHOT.jar [port]" - wenn auf einem anderen Port gewünscht ist, dann kann dies noch hinter der Datei angeben werde werden
