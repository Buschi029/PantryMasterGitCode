import unittest
import json
from app import app

class TestFlaskRoutes(unittest.TestCase):

    def setUp(self):
        self.app = app.test_client()
        self.app.testing = True 

    def test_insert_Item(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "productCode": 12345,
            "userID": "z",
            "productName": "Apfel",
            "expirationDate": "2023-07-02",
            "quantity": 1,
            "quantityUnit": "kg",
            "appendDate": "2023-07-02T09:45:40.405"
        })
        
        response = self.app.put('/inventory', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.get_json(), {"message": "Data inserted successfully"})

    def test_get_allInvItem(self):
        response = self.app.get('/inventory')
        self.assertEqual(response.status_code, 200)

    def test_get_allInvItem(self):
        response = self.app.post('/inventory', query_string=dict(userID='z'))
        self.assertEqual(response.status_code, 200)

    def test_edit_Item(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "productCode": 12345,
            "userID": "z",
            "productName": "Birne",
            "expirationDate": "2023-07-02",
            "quantity": 1,
            "quantityUnit": "kg",
            "appendDate": "2023-07-02T09:45:40.405"
        })
        
        response = self.app.patch('/inventory', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

    def test_delete_invItem(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "appendTime": "2023-07-02T09:45:40.405",
        })
        
        response = self.app.delete('/inventory', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
