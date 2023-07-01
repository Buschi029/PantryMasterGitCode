import unittest
import json
from app import app

class TestFlaskRoutes(unittest.TestCase):

    def setUp(self):
        self.app = app.test_client()
        self.app.testing = True 

    def test_get_allUser(self):
        response = self.app.get('/user')
        self.assertEqual(response.status_code, 200)

    def test_add_User(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "userID": "ABC",
            "userName": "Michael",
            "age": 18,
            "gender": "male",
        })
        response = self.app.put('/user', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

    def test_get_UserInfo(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "userID": "ABC"
        })
        
        response = self.app.post('/user', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

    def test_delete_User(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "userID": "ABC"
        })
        
        response = self.app.delete('/user', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
