import unittest
import json
from app import app

class TestFlaskRoutes(unittest.TestCase):

    def setUp(self):
        self.app = app.test_client()
        self.app.testing = True 

#Test ob put request funktioniert, Anlegen eines Nutzers
    def test_add_User(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "userID": "zz",
            "userName": "Michael",
            "age": 18,
            "gender": "male",
        })
        response = self.app.put('/user', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

#Test ob get request funktioniert
    def test_get_allUser(self):
        response = self.app.get('/user')
        self.assertEqual(response.status_code, 200)

#Test ob post request funktioniert, UserInfos bekommen
    def test_get_UserInfo(self):
        response = self.app.post('/user', query_string=dict(userID='zz'))
        self.assertEqual(response.status_code, 200)

#Test ob delete request funktioniert, Nutzer soll gelöscht
    def test_delete_User(self):
        response = self.app.delete('/user', query_string=dict(userID='zz'))
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
