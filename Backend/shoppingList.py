
import psycopg2
from flask import Flask, jsonify, request
from __main__ import app



host = "ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = "5432"
database = "shoppingListDB"
user = "ADMIN"
password = "uihkP3cnT0Wo"

conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
)



@app.route("/shoppingList", methods=["GET"])
def get_allItems():
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_shoppingList")
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {"productName": row[0], "userID": row[1], "quantity": row[2], "quantityUnit": row[3]}
        results.append(result)
    return jsonify(results)

@app.route("/shoppingList", methods=["POST"])
def get_oneItem():
    data = request.get_json()
    userID = data["userID"]
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_shoppingList WHERE userID=%s", (userID,))
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {"productName": row[0], "userID": row[1], "quantity": row[2], "quantityUnit": row[3]}
        results.append(result)
    return jsonify(results)


@app.route("/shoppingList", methods=["PUT"])
def add_item():
    data = request.get_json()
    userID = data["userID"]
    productName = data["productName"]
    quantity = data["quantity"]
    quantityUnit = data["quantityUnit"]

    output = checkForDuplicates(productName, userID, quantity, quantityUnit)

    return output

def checkForDuplicates(productName, userID, quantity, quantityUnit):
    cursor = conn.cursor()
    cursor.execute("SELECT productName FROM tbl_shoppingList WHERE productName=%s AND userID=%s", (productName, userID))

    if cursor.rowcount == 0:
        return addEntry(productName, userID, quantity, quantityUnit)
    else:
        return updateEntry(productName, userID, quantity)

def updateEntry(productName, userID, quantity):
    cursor = conn.cursor()
    cursor.execute("Update tbl_shoppingList SET quantity=quantity+%s WHERE productName=%s AND userID=%s", (quantity, productName, userID))
    cursor.close()

    return "Eintrag erhöht"

def addEntry(productName, userID, quantity, quantityUnit):
    cursor = conn.cursor()
    cursor.execute("INSERT INTO tbl_shoppingList (userID, productName, quantity, quantityUnit) VALUES (%s, %s, %s, %s)", (userID, productName, quantity, quantityUnit))
    conn.commit()
    cursor.close()

    return "Eintrag angelegt"


@app.route("/shoppingList", methods=["DELETE"])
def delete_item():
    data = request.get_json()
    userID = data["userID"]
    productName = data["productName"]

    cursor = conn.cursor()
    cursor.execute("DELETE FROM tbl_shoppingList WHERE productName=%s AND userID=%s", (productName, userID))
    conn.commit()
    cursor.close()

    return "Entfernt"
