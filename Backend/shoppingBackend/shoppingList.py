
import psycopg2
from flask import Flask, jsonify, request
from app import app



host = "ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = "5432"
database = "shoppingListDB"
user = "ADMIN"
password = "uihkP3cnT0Wo"

def tryConnect():
    conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
    )
    return conn


@app.route("/shoppingList", methods=["GET"])
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

@app.route("/shoppingList", methods=["POST"])
def get_oneItem():
    data = request.get_json()
    userID = data["userID"]
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

def updateEntry(productName, userID, quantity):
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("Update tbl_shoppingList SET quantity=quantity+%s WHERE productName=%s AND userID=%s", (quantity, productName, userID))
    cursor.close()
    conn.close()

    return "Eintrag erhöht"

def addEntry(productName, userID, quantity, quantityUnit):
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("INSERT INTO tbl_shoppingList (userID, productName, quantity, quantityUnit) VALUES (%s, %s, %s, %s)", (userID, productName, quantity, quantityUnit))
    conn.commit()
    cursor.close()
    conn.close()

    return "Eintrag angelegt"


@app.route("/shoppingList", methods=["DELETE"])
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

    return "Entfernt"

