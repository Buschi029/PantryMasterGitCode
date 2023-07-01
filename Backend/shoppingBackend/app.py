import json

import psycopg2
import requests
from flask import Flask, jsonify, request
from shoppingList import shoppingList
from user import userB

from apiflask import APIFlask, Schema, abort
from apiflask.fields import Integer, String
from apiflask.validators import Length, OneOf

app = APIFlask(__name__)
app.register_blueprint(shoppingList)
app.register_blueprint(userB)


@app.route("/")
def empty():
    return "leerer Pfad!"

if __name__ == '__main__':
    app.run()
