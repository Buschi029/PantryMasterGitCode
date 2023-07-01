
import psycopg2
from flask import Flask, jsonify, request
from apiflask import APIBlueprint, Schema
from apiflask.fields import Integer, String
from apiflask.validators import Length, OneOf

userB = APIBlueprint('user', __name__)

class user(Schema):
    userID = String(required=True)
    userName = String(required=True)
    age = Integer(required=True)
    gender = String(required=True)

class userKey(Schema):
    userID = String(required=True)

host = "ep-old-rice-105179.eu-central-1.aws.neon.tech"
port = "5432"
database = "userDB"
userDB = "ADMIN"
password = "uihkP3cnT0Wo"
sslmode="require"


def tryConnect():
    conn = psycopg2.connect(
    host=host, port=port, database=database, user=userDB, password=password
    )
    return conn

@userB.output(user)
@userB.route("/user", methods=["GET"])
def get_AllUser():
    conn = tryConnect() 
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM tbl_user")
    data = cursor.fetchall()
    cursor.close()
    conn.close()

    results = []
    for row in data:
        result = {"userID": row[0], "userName": row[1], "age": row[2], "gender": row[3]}
        results.append(result)
    return jsonify(results)

@userB.input(userKey)
@userB.output(user)
@userB.route("/user", methods=["POST"])
def get_userInfo():
    data = request.get_json()
    userID = data["userID"]

    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM tbl_user WHERE userID = %s", (userID,))
    data = cursor.fetchall()
    cursor.close()
    conn.close()

    results = []
    for row in data:
        result = {"userID": row[0], "userName": row[1], "age": row[2], "gender": row[3]}
        results.append(result)
    return jsonify(results)

@userB.output(user)
@userB.route("/user", methods=["PUT"])
def add_user():
    data = request.get_json()
    userID = data["userID"]
    userName = data["userName"]
    age = data["age"]
    gender = data["gender"]

    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("INSERT INTO tbl_user (userID, userName, age, gender) VALUES (%s, %s, %s, %s)", (userID, userName, age, gender))
    conn.commit()
    cursor.close()
    conn.close()

    return "User angelegt"

@userB.input(userKey)
@userB.route("/user", methods=["DELETE"])
def delete_user():
    data = request.get_json()
    userID = data["userID"]

    conn = tryConnect() 
    cursor = conn.cursor()
    cursor.execute("DELETE FROM tbl_user WHERE userID=%s", (userID,))
    conn.commit()
    cursor.close()
    conn.close()

    return "User entfernt"
