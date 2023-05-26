import os

import psycopg2
from dotenv import load_dotenv
from flask import Flask, jsonify, request

load_dotenv()

app = Flask(__name__)

host = os.environ.get("DB_HOST")
port = os.environ.get("DB_PORT")
database = os.environ.get("DB_DATABASE")
user = os.environ.get("DB_USER")
password = os.environ.get("DB_PASSWORD")

conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
)


@app.route("/")
def empty():
    return "leerer Pfad!"


@app.route("/data", methods=["GET"])
def get_data():
    cursor = conn.cursor()
    cursor.execute("SELECT id, name FROM test")
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {"id": row[0], "name": row[1]}
        results.append(result)
    return jsonify(results)


# @app.route("/shoppingList", methods=["GET"])
# def get_data():
#     cursor = conn.cursor()
#     cursor.execute("SELECT productCode, userID, productName, quantity, quantityUnit, productCategory FROM tbl_pantry")
#     data = cursor.fetchall()
#     cursor.close()

#     results = []
#     for row in data:
#         result = {"id": row[0], "name": row[1]}
#         results.append(result)
#     return jsonify(results)


# @app.route("/shoppingList", methods=["POST"])
# def add_data():
#     data = request.get_json()
#     productCode = data["productCode"]
#     userID = data["userID"]
#     productName = data["productName"]
#     quantity = data["quantity"]
#     quantityUnit = data["quantityUnit"]
#     productCategory = data["productCategory"]

#     cursor = conn.cursor()
#     cursor.execute("INSERT INTO tbl_pantry (productCode, userID, productName, quantity, quantityUnit, productCategory) VALUES (%s, %s, %s, %s, %s, %s)", (productCode, userID, productName, quantity, quantityUnit, productCategory))
#     conn.commit()
#     cursor.close()

#     return "Daten hinzugef�gt."

@app.route("/test", methods=["POST"])
def add_data():
    data = request.get_json()
    name = data["name"]
    quantity = data["quantity"]

    cursor = conn.cursor()
    cursor.execute("INSERT INTO test (name, quantity) VALUES (%s, %s)", (name, quantity))
    conn.commit()
    cursor.close()

    return "Daten hinzugef�gt."


if __name__ == "__main__":
    app.run()
