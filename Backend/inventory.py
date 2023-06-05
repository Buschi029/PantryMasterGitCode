import json

import psycopg2
import requests
from flask import Flask, jsonify, request


app = Flask(__name__)

host = #
port = #
database = #
user = #
password = #

conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
)

@app.route("/")
def empty():
    return "leerer Pfad!"

@app.route("/inventory", methods=["GET"])
def get_data():
    cursor = conn.cursor()
    cursor.execute("SELECT productCode, userID, productName, expirationDate, quantity, quantityUnit, \
                   appendDate, productCategory FROM tbl_pantry")
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {"id": row[0], "name": row[1]}
        results.append(result)
    return jsonify(results)

@app.route("/inventory", methods=["POST"])
def get_data():
    data = request.get_json()
    barcode = data["barcode"]
    userID = data["userID"]

    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_product WHERE productcode = %s AND userid = %s", (barcode,userID))
    data = cursor.fetchall()
    cursor.close()

    # results = []
    #     for row in data:
    #     result = {"id": row[0], "name": row[1]}
    #     results.append(result)
    return jsonify(cursor)


if __name__ == "__main__":
    app.run()

