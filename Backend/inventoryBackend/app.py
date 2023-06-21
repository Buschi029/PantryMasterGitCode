import json

import psycopg2
import requests
from flask import Flask, jsonify, request


app = Flask(__name__)

import inventory
import product

@app.route("/")
def empty():
    return "leerer Pfad!"

if __name__ == '__main__':
    app.run()
