import json

import psycopg2
import requests
from flask import Flask, jsonify, request
from apiflask import APIBlueprint, Schema
from apiflask.fields import Integer, String
from apiflask.validators import Length, OneOf

#Product wird als Blueprint erstellt, damit die app.py darauf zugreifen und nutzen kann
product = APIBlueprint('product', __name__)

#Anmeldedaten für die Datenbank
host = "ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = "5432"
database = "pantryDB"
user = "ADMIN"
password = "uihkP3cnT0Wo"
sslmode="require"

#ProductItem Klasse
class productItem(Schema):
    productCode = Integer(required=True)
    carbohydrates = Integer(required=True)
    kcal = Integer(required=True)
    fat = Integer(required=True)
    nutriscore = String(required=True)
    protein = Integer(required=True)
    sugar = Integer(required=True)
    name = String(required=True)
    pictureLink = String(required=True)

#ProductItemKey Klasse
class productKey(Schema):
    barcode = Integer(required=True)

#Aufbau einer Connection zur Datenbank
def tryConnect():
    conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
    )
    return conn

a = [1, 2, 3, 4, 5, 6, 7, 8, 9]

#API an OpenFoodFact um Produktinfos zu erhalten
def get_produktinfo(barcode):

    url = f"https://world.openfoodfacts.org/api/v2/product/{barcode}.json"
    response = requests.get(url)
    abfrage = response.json()
    print(abfrage)

    if "product" in abfrage:
        global a
        a[0] = barcode
        a[1] = abfrage["product"]["nutriments"].get("carbohydrates", 9999)
        a[2] = abfrage["product"]["nutriments"].get("energy-kcal", 9999)
        a[3] = abfrage["product"]["nutriments"].get("fat", 9999)
        if abfrage["product"]["misc_tags"][0] == "en:nutriscore-not-computed":
            a[4] = "z"
        else:
            a[4] = abfrage["product"].get("nutriscore_grade", "z")
        a[5] = abfrage["product"]["nutriments"].get("proteins", 9999)
        a[6] = abfrage["product"]["nutriments"].get("sugars", 9999)
        a[7] = abfrage["product"].get("image_front_url", "Keine Angaben")
        a[8] = abfrage["product"].get("product_name_de", "Keine Angaben")
        return a
    else:
        return None

#POST Route um eine Produkt zu erhalten und falls es noch nicht in DB enthalten ist wird die get_produktinfo aufgerufen
@product.output(productItem)
@product.input(productKey)
@product.route("/product", methods=["POST"])
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
