import json

import psycopg2
import requests
from flask import Flask, jsonify, request


app = Flask(__name__)

host = #"ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = #"5432"
database = #"pantryDB"
user = #"ADMIN"
password = #"uihkP3cnT0Wo"

conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
)

@app.route("/")
def empty():
    return "leerer Pfad!"

@app.route("/inventory", methods=["GET"])
def get_data():
    cursor = conn.cursor()
    cursor.execute("SELECT productCode, userID, productName, expirationDate, quantity, quantityUnit, appendDate, productCategory FROM tbl_pantry")
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {"id": row[0], "name": row[1]}
        results.append(result)
    return jsonify(results)

@app.route("/inventory", methods=["POST"])
def post_data():
    data = request.get_json()
    userID = data["userID"]

    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_pantry WHERE userid = %s", (userID))
    data = cursor.fetchall()
    cursor.close()

    # results = []
    #     for row in data:
    #     result = {"id": row[0], "name": row[1]}
    #     results.append(result)
    return jsonify(cursor)


@app.route("/inventory", methods=["DELETE"])
def delete_data():
    data = request.get_json()
    userID = data["userID"]
    barcode = data["barcode"]

    cursor = conn.cursor()
    cursor.execute("DELETE FROM tbl_pantry WHERE productCode=%s AND userID=%s", (barcode, userID))
    conn.commit()
    cursor.close()

    return "Entfernt"

app.run(host='0.0.0.0', port=81)
