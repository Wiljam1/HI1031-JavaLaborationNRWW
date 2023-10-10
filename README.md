# JavaLaborationNRWW

Java laboration i HI1031 skriven av Niklas Roslund & Wiljam Wilmi

Kontakt: wiljam@kth.se

Om man vill köra programmet (räcker nog med att läsa kod dock);
1. Använd IntelliJ Ultimate.
2. Project Structure - SDK: OpenJDK version 21 - Modules: Web: Tomcat Context Descriptor
3. Tomcat version 10.1.13 bör vara i en mapp på datorn och länkad i projektet.
4. Använd en lokal MongoDB-databas och byt ut connection-String i DBManager.java-filen.
5. Skapa en databas som heter "JavaLaboration", i den bör collections som heter "items" samt "users" finnas.
6. Importera admin-dokumentet nedan i users-collection för att ha en admin att logga in med som sedan kan skapa fler användare eller lägga till items på sidan.

{
  "_id": {
    "$oid": "651d1eac46a8d540a3e92a84"
  },
  "username": "administrator",
  "name": "test",
  "password": "abc",
  "authorization": "admin",
  "orders": []
}
