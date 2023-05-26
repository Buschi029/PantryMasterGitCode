import os
import psycopg2
import requests
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

barcode = "42143628"

def get_produktinfo(barcode):

    url = f"https://world.openfoodfacts.org/api/v2/product/{barcode}.json"
    response = requests.get(url)
    abfrage = response.json()

    if "product" in abfrage:
        product_name = abfrage["product"]["product_name"]
        product_quantity = abfrage["product"]["product_quantity"]
        return product_name, product_quantity
    else:
        return None
    
def save_produktinfo(barcode):

    product_name, product_quantity = get_produktinfo(barcode)

    if product_name:

        cursor = conn.cursor()
        cursor.execute("INSERT INTO test (name, quantity) VALUES (%s, %s)", (product_name, product_quantity ))
        conn.commit()
        conn.close()

        print("Produktname gespeichert.")
    else:
        print("Fehler.")

save_produktinfo(barcode)

@app.route("/")
def empty():
    return "leerer Pfad!"

@app.route("/data", methods=["GET"])
def get_dataNew():
    cursor = conn.cursor()
    cursor.execute("SELECT id, name FROM test")
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {"id": row[0], "name": row[1]}
        results.append(result)
    return jsonify(results)


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
def add_data():
    data = request.get_json()
    productCode = data["productCode"]
    userID = data["userID"]
    productName = data["productName"]
    expirationDate = data["expirationDate"]
    quantity = data["quantity"]
    quantityUnit = data["quantityUnit"]
    appendDate = data["appendDate"]
    productCategory = data["productCategory"]

    cursor = conn.cursor()
    cursor.execute("INSERT INTO tbl_pantry (productCode, userID, productName, expirationDate, quantity, quantityUnit, \
                   appendDate, productCategory) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)",
                    (
                        productCode,
                        userID,
                        productName, 
                        expirationDate, 
                        quantity, 
                        quantityUnit, 
                        appendDate, 
                        productCategory
                    ),
            )
    conn.commit()
    cursor.close()

    return "Daten hinzugefügt."


if __name__ == "__main__":
    app.run()
