import json

import psycopg2
import requests
from flask import Flask, jsonify, request
from apiflask import APIBlueprint, Schema
from apiflask.fields import Integer, String, Date, DateTime
from apiflask.validators import Length, OneOf
from datetime import datetime

#Inventory wird als Blueprint erstellt, damit die app.py darauf zugreifen und nutzen kann
inventory = APIBlueprint('inventory', __name__)

#Anmeldedaten für die Datenbank
host = "ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = "5432"
database = "pantryDB"
user = "ADMIN"
password = "uihkP3cnT0Wo"

#InventoryItem Klasse
class inventoryItem(Schema):
    productCode = Integer(required=True)
    productName = String(required=True)
    userID = String(required=True)
    expirationDate = Date(required=True)
    appendDate = DateTime(required=True)
    quantity = Integer(required=True)
    quantityUnit = String(required=True)

#Aufbau einer Connection zur Datenbank
def tryConnect():
    conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password, sslmode='require'
    )
    return conn

#Get Route um alle InventoryItems abzufragen
@inventory.route("/inventory", methods=["GET"])
@inventory.output(inventoryItem)
def get_allInvItem():
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("SELECT productCode, userID, productName, expirationDate, quantity, quantityUnit, appendDate FROM tbl_pantry")
    data = cursor.fetchall()
    cursor.close()
    conn.close()

    

    results = []
    x = 0
    for row in data:
        apdDate = row[6].strftime("%Y-%m-%dT%H:%M:%S.%f")
        expDate = row[3].strftime("%Y-%m-%d")

        result = {"productCode": row[0], "userID": row[1], "productName": row[2], "expirationDate": expDate, "quantity": row[4], "quantityUnit": row[5], "appendDate": apdDate}
        results.append(result)
        x = x + 1
    return jsonify(results)

#POST Route um alle InventoryItems für einen bestimmte User abzufragen
@inventory.route("/inventory", methods=["POST"])
@inventory.output(inventoryItem)
def get_oneInvItem():
    userID = request.args.get('userID')
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("SELECT productCode, userID, productName, expirationDate, quantity, quantityUnit, appendDate FROM tbl_pantry WHERE userID=%s", (userID,))
    data = cursor.fetchall()
    cursor.close()
    conn.close()

    

    results = []
    x = 0
    for row in data:
        apdDate = row[6].strftime("%Y-%m-%dT%H:%M:%S.%f")
        expDate = row[3].strftime("%Y-%m-%d")

        result = {"productCode": row[0], "userID": row[1], "productName": row[2], "expirationDate": expDate, "quantity": row[4], "quantityUnit": row[5], "appendDate": apdDate}
        results.append(result)
        x = x + 1
    return jsonify(results)

#PUT Route um ein Item zur Datenbank hinzuzufügen
@inventory.route("/inventory", methods=["PUT"])
@inventory.input(inventoryItem)
def insert_Item(inventoryItem):

    json_data = request.get_json()

    productCode = json_data.get("productCode")
    userID = json_data.get("userID")
    productName = json_data.get("productName")
    expirationDate = json_data.get("expirationDate")
    quantity = json_data.get("quantity")
    quantityUnit = json_data.get("quantityUnit")
    appendDate = json_data.get("appendDate")

    conn = tryConnect() 
    cursor = conn.cursor()

    cursor.execute("INSERT INTO tbl_pantry (productCode, productName, userID, expirationDate, appendDate, quantity, quantityUnit) VALUES (%s, %s, %s, %s, %s, %s, %s)", (productCode, productName, userID, expirationDate, appendDate, quantity, quantityUnit))
    
    conn.commit()
    cursor.close()
    conn.close()

    response = {"message": "Data inserted successfully"}
    return jsonify(response)

#PATCH Route um ein Item zu verändern
@inventory.route("/inventory", methods=["PATCH"])
@inventory.input(inventoryItem)
def edit_Item(inventoryItem):
    json_data = request.get_json()
    print(json_data.get("appendDate"))

    productCode = json_data.get("productCode")
    userID = json_data.get("userID")
    productName = json_data.get("productName")
    expirationDate = json_data.get("expirationDate")
    quantity = json_data.get("quantity")
    quantityUnit = json_data.get("quantityUnit")
    appendDate = json_data.get("appendDate")
    conn = tryConnect() 
    cursor = conn.cursor()

    cursor.execute("UPDATE tbl_pantry SET productCode=%s, productName=%s, userID=%s, expirationDate=%s, quantity=%s, quantityUnit=%s WHERE appendDate=%s", (productCode, productName, userID, expirationDate, quantity, quantityUnit, appendDate))
    conn.commit()
    cursor.close()
    conn.close()

    return get_allInvItem()



#DELETE Route um ein Item zu löschen
@inventory.route("/inventory", methods=["DELETE"])
@inventory.input(inventoryItem)
def delete_invItem(inventoryItem):
    data = request.get_json()
    productCode = data.get("productCode")
    userID = data.get("userID")
    productName = data.get("productName")
    expirationDate = data.get("expirationDate")
    quantity = data.get("quantity")
    quantityUnit = data.get("quantityUnit")
    appendDate = data.get("appendDate")

    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("DELETE FROM tbl_pantry WHERE appendDate=%s", (appendDate,))
    conn.commit()
    cursor.close()
    conn.close()

    return get_allInvItem()
