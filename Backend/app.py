import json

import psycopg2
import requests
from flask import Flask, jsonify, request


app = Flask(__name__)

import inventory
import product
import shoppingList
import user

@app.route("/")
def empty():
    return "leerer Pfad!"

app.run(host='0.0.0.0', port=80)
