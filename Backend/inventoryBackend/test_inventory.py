import unittest
import json
from app import app

class TestFlaskRoutes(unittest.TestCase):

    def setUp(self):
        self.app = app.test_client()
        self.app.testing = True 

    def test_get_allInvItem(self):
        response = self.app.get('/inventory')
        self.assertEqual(response.status_code, 200)

    def test_insert_Item(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "productCode": "12345",
            "userID": "1",
            "productName": "Apfel",
            "expirationDate": "2024-07-10",
            "quantity": "1",
            "quantityUnit": "kg",
            "appendDate": "2023-06-26"
        })
        
        response = self.app.post('/inventory', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.get_json(), {"message": "Data inserted successfully"})

    def test_delete_invItem(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "userID": "1",
            "barcode": "12345"
        })
        
        response = self.app.delete('/inventory', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
