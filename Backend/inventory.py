import json
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

a = [1, 2, 3, 4, 5, 6, 7]

def get_produktinfo(barcode):

    url = f"https://world.openfoodfacts.org/api/v2/product/{barcode}.json"
    response = requests.get(url)
    abfrage = response.json()
    print(abfrage)

    if "product" in abfrage:
        global a
        a[0] = barcode
        a[1] = abfrage["product"]["nutriments"]["carbohydrates"]
        a[2] = abfrage["product"]["nutriments"]["energy-kcal"]
        a[3] = abfrage["product"]["nutriments"]["fat"]
        if abfrage["product"]["misc_tags"][0] == "en:nutriscore-not-computed":
            a[4] = "z"
        else:
            a[4] = abfrage["product"]["nutriscore_grade"]
        a[5] = abfrage["product"]["nutriments"]["proteins"]
        a[6] = abfrage["product"]["nutriments"]["sugars"]
        return a
    else:
        return None


@app.route("/inventory", methods=["POST"])
def add_data():
    data = request.get_json()
    barcode = data["barcode"]

    cursor = conn.cursor()
    cursor.execute("SELECT productcode FROM tbl_product WHERE productcode = %s", (barcode,))
    existing_entry = cursor.fetchone()

    if existing_entry is None:
        get_produktinfo(barcode)

        cursor.execute("INSERT INTO tbl_product (productcode, carbohydrates, kcal, fat, nutriscore, protein, sugar) VALUES (%s, %s, %s, %s, %s, %s, %s)",
            (
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                a[6]
            ),
        )
        conn.commit()
        cursor.execute("SELECT productcode, carbohydrates, kcal, fat, nutriscore, protein, sugar FROM tbl_product WHERE productcode = %s", (barcode,))
        data = cursor.fetchall()
        cursor.close()
        article = {
            'productcode': data[0][0],
            'carbohydrates': data[0][1],
            'kcal': data[0][2],
            'fat': data[0][3],
            'nutriscore': data[0][4],
            'protein': data[0][5],
            'sugar': data[0][6],
        }
        json.dumps(article)

        return article

    else:
        cursor.execute("SELECT productcode, carbohydrates, kcal, fat, nutriscore, protein, sugar FROM tbl_product WHERE productcode = %s", (barcode,))
        data = cursor.fetchall()
        cursor.close()
        article = {
            'productcode': data[0][0],
            'carbohydrates': data[0][1],
            'kcal': data[0][2],
            'fat': data[0][3],
            'nutriscore': data[0][4],
            'protein': data[0][5],
            'sugar': data[0][6],
        }
        json.dumps(article)

        return article


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


if __name__ == "__main__":
    app.run()

