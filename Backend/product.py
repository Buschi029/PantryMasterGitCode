import json

import psycopg2
import requests
from flask import Flask, jsonify, request
from __main__ import app

#python -m unittest discover -s 'Backend/' -p 'test_product.py'

#app = Flask(__name__)

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

a = [1, 2, 3, 4, 5, 6, 7, 8, 9]

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
        a[7] = abfrage["product"]["product_name_de"]
        a[8] = abfrage["product"]["image_front_url"]
        return a
    else:
        return None


@app.route("/product", methods=["POST"])
def get_oneProduct():
    data = request.get_json()
    barcode = data["barcode"]

    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("SELECT productcode FROM tbl_product WHERE productcode = %s", (barcode,))
    existing_entry = cursor.fetchone()

    if existing_entry is None:
        get_produktinfo(barcode)

        cursor.execute("INSERT INTO tbl_product (productcode, carbohydrates, kcal, fat, nutriscore, protein, sugar, pictureLink, productName) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)",
            (
                a[0],
                a[1],
                a[2],
                a[3],
                a[4],
                a[5],
                a[6],
                a[7],
                a[8]
            ),
        )
        conn.commit()
        cursor.execute("SELECT productcode, carbohydrates, kcal, fat, nutriscore, protein, sugar, productName, pictureLink FROM tbl_product WHERE productcode = %s", (barcode,))
        data = cursor.fetchall()
        cursor.close()
        conn.close()
        article = {
            'productcode': data[0][0],
            'carbohydrates': data[0][1],
            'kcal': data[0][2],
            'fat': data[0][3],
            'nutriscore': data[0][4],
            'protein': data[0][5],
            'sugar': data[0][6],
            'name': data[0][7],
            'pictureLink': data[0][8]
        }
        json.dumps(article)

        return article

    else:
        cursor.execute("SELECT productcode, carbohydrates, kcal, fat, nutriscore, protein, sugar, productName, pictureLink FROM tbl_product WHERE productcode = %s", (barcode,))
        data = cursor.fetchall()
        cursor.close()
        conn.close()
        article = {
            'productcode': data[0][0],
            'carbohydrates': data[0][1],
            'kcal': data[0][2],
            'fat': data[0][3],
            'nutriscore': data[0][4],
            'protein': data[0][5],
            'sugar': data[0][6],
            'name': data[0][7],
            'pictureLink': data[0][8]
        }
        json.dumps(article)

        return article


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=81)
