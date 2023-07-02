import json

import psycopg2
import requests
from flask import Flask, jsonify, request

from inventory import inventory
from product import product

from apiflask import APIFlask, Schema, abort
from apiflask.fields import Integer, String
from apiflask.validators import Length, OneOf

app = APIFlask(__name__, docs_path="/")
app.register_blueprint(inventory)
app.register_blueprint(product)


if __name__ == '__main__':
    app.run()
