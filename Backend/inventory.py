import psycopg2
from flask import Flask, jsonify, request

app = Flask(__name__)

conn = psycopg2.connect(
    host="ep-dark-surf-104265.eu-central-1.aws.neon.tech",
    port=5432,
    database="pantry",
    user="admin",
    password="Xb5hNqigjzl4"
)

@app.route('/')
def empty():
    return 'leerer Pfad!'

@app.route('/data', methods=['GET'])
def get_data():
    cursor = conn.cursor()
    cursor.execute("SELECT id, name FROM test")
    data = cursor.fetchall()
    cursor.close()

    results = []
    for row in data:
        result = {'id': row[0], 'name': row[1]}
        results.append(result)
    return jsonify(results)

@app.route('/data', methods=['POST'])
def add_data():

    data = request.get_json()
  
    id = data['id']
    name = data['name']

    cursor = conn.cursor()
    cursor.execute("INSERT INTO test (id, name) VALUES (%s, %s)", (id, name))
    conn.commit()
    cursor.close()

    return 'Daten hinzugefügt.'

if __name__ == '__main__':
    app.run()

