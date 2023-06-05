
import psycopg2
from flask import Flask, jsonify, request


app = Flask(__name__)

host = #"ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = #"5432"
database = #"userDB"
user = #"ADMIN"
password = #"uihkP3cnT0Wo"

conn = psycopg2.connect(
    host=host, port=port, database=database, user=user, password=password
)


@app.route("/")
def empty():
    return "leerer Pfad!"


@app.route("/user", methods=["GET"])
def get_alldata():
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_user")
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {"userID": row[0], "userName": row[1], "age": row[2], "gender": row[3]}
        results.append(result)
    return jsonify(results)


@app.route("/user", methods=["POST"])
def get_alldata():
    data = request.get_json()
    userID = data["userID"]

    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_user WHERE userID = %s", (userID,))
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {"userID": row[0], "userName": row[1], "age": row[2], "gender": row[3]}
        results.append(result)
    return jsonify(results)


@app.route("/user", methods=["PUT"])
def add_data():
    data = request.get_json()
    userID = data["userID"]
    userName = data["userName"]
    age = data["age"]
    gender = data["gender"]

    cursor = conn.cursor()
    cursor.execute("INSERT INTO tbl_user (userID, userName, age, gender) VALUES (%s, %s, %s, %s)", (userID, userName, age, gender))
    conn.commit()
    cursor.close()

    return "User angelegt"

@app.route("/user", methods=["DELETE"])
def delete_data():
    data = request.get_json()
    userID = data["userID"]
    productName = data["productName"]

    cursor = conn.cursor()
    cursor.execute("DELETE FROM tbl_user WHERE userID=%s", (userID,))
    conn.commit()
    cursor.close()

    return "User entfernt"



app.run(host='0.0.0.0', port=81)