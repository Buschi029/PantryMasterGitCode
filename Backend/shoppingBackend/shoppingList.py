
import psycopg2
from flask import Flask, jsonify, request, Blueprint
from apiflask import APIBlueprint, Schema
from apiflask.fields import Integer, String
from apiflask.validators import Length, OneOf

#ShoppingList wird als Blueprint erstellt, damit die app.py darauf zugreifen und nutzen kann
shoppingList = APIBlueprint('shoppingList', __name__)

#ShoppingItem Klasse
class shoppingItem(Schema):
    productName = String(required=True)
    userID = String(required=True)
    quantity = Integer(required=True)
    quantityUnit = String(required=True)

#ShoppingItem Klasse zum löschen
class shoppingItemKeyDelete(Schema):
    productName = String(required=True)
    userID = String(required=True)

#Anmeldedaten für die Datenbank
host = "ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = "5432"
database = "shoppingListDB"
user = "ADMIN"
password = "uihkP3cnT0Wo"

#Aufbau einer Connection zur Datenbank
def tryConnect():
    conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
    )
    return conn

#Get Route um alle shoppingItems zu erhalten
@shoppingList.output(shoppingItem)
@shoppingList.route("/shoppingList", methods=["GET"])
def get_allItems():
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_shoppingList")
    data = cursor.fetchall()
    cursor.close()
    conn.close()

    results = []
    for row in data:
        result = {"productName": row[0], "userID": row[1], "quantity": row[2], "quantityUnit": row[3]}
        results.append(result)
    return jsonify(results)

#Post Route um alle Items für einen bestimmte User zu erhalten
@shoppingList.output(shoppingItem)
@shoppingList.route("/shoppingList", methods=["POST"])
def get_oneItem():
    userID = request.args.get('userID')
    conn = tryConnect() 

    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_shoppingList WHERE userID=%s", (userID,))
    data = cursor.fetchall()
    cursor.close()
    conn.close()

    results = []
    for row in data:
        result = {"productName": row[0], "userID": row[1], "quantity": row[2], "quantityUnit": row[3]}
        results.append(result)
    return jsonify(results)

#Put Route um ein Item in der ShoppingList hinzuzufügen
@shoppingList.input(shoppingItem)
@shoppingList.route("/shoppingList", methods=["PUT"])
def add_item():
    data = request.get_json()
    userID = data["userID"]
    productName = data["productName"]
    quantity = data["quantity"]
    quantityUnit = data["quantityUnit"]

    output = checkForDuplicates(productName, userID, quantity, quantityUnit)

    return output

#schaut ob das Item bereits in der Datenbank abliegt, wenn ja wird die Menge um die Quantity erhöht
def checkForDuplicates(productName, userID, quantity, quantityUnit):
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("SELECT productName FROM tbl_shoppingList WHERE productName=%s AND userID=%s", (productName, userID))

    if cursor.rowcount == 0:
        cursor.close()
        conn.close()
        return addEntry(productName, userID, quantity, quantityUnit)
    else:
        cursor.close()
        conn.close()
        return updateEntry(productName, userID, quantity)

#Menge eines Items wird erhöht wenn das gleiche nochmal hinzugefügt wird
def updateEntry(productName, userID, quantity):
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("Update tbl_shoppingList SET quantity=quantity+%s WHERE productName=%s AND userID=%s", (quantity, productName, userID))
    cursor.close()
    conn.close()

    return "Eintrag erhöht"

#fügt neuen Eintrag für ein Item in die Datenbank ein
def addEntry(productName, userID, quantity, quantityUnit):
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("INSERT INTO tbl_shoppingList (userID, productName, quantity, quantityUnit) VALUES (%s, %s, %s, %s)", (userID, productName, quantity, quantityUnit))
    conn.commit()
    cursor.close()
    conn.close()

    return "Eintrag angelegt"

#DELETE Route um ein Item aus der ShoppingList zu entfernen
@shoppingList.input(shoppingItemKeyDelete)
@shoppingList.route("/shoppingList", methods=["DELETE"])
def delete_item():
    data = request.get_json()
    userID = data["userID"]
    productName = data["productName"]

    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("DELETE FROM tbl_shoppingList WHERE productName=%s AND userID=%s", (productName, userID))
    conn.commit()
    cursor.close()
    conn.close()

    return get_allItems()

