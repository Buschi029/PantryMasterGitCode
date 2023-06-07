import json

import psycopg2
import requests
from flask import Flask, jsonify, request
from __main__ import app



host = "ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = "5432"
database = "pantryDB"
user = "ADMIN"
password = "uihkP3cnT0Wo"

def tryConnect():
    conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
    )
    return conn


@app.route("/inventory", methods=["GET"])
def get_allInvItem():
    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("SELECT productCode, userID, productName, expirationDate, quantity, quantityUnit, appendDate, productCategory FROM tbl_pantry")
    data = cursor.fetchall()
    cursor.close()
    conn.close()

    results = []
    for row in data:
        result = {"id": row[0], "name": row[1]}
        results.append(result)
    return jsonify(results)

@app.route("/inventory", methods=["POST"])
def get_oneInvItem():
    data = request.get_json()
    userID = data["userID"]

    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_pantry WHERE userid = %s", (userID))
    data = cursor.fetchall()
    cursor.close()
    conn.close()    

    # results = []
    #     for row in data:
    #     result = {"id": row[0], "name": row[1]}
    #     results.append(result)
    return jsonify(cursor)


@app.route("/inventory", methods=["DELETE"])
def delete_invItem():
    data = request.get_json()
    userID = data["userID"]
    barcode = data["barcode"]

    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("DELETE FROM tbl_pantry WHERE productCode=%s AND userID=%s", (barcode, userID))
    conn.commit()
    cursor.close()
    conn.close()

    return "Entfernt"
