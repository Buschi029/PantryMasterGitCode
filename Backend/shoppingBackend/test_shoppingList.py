import unittest
import json
from app import app

class TestFlaskRoutes(unittest.TestCase):

    def setUp(self):
        self.app = app.test_client()
        self.app.testing = True 

#Testet die get Route
    def test_get_allItems(self):
        response = self.app.get('/shoppingList')
        self.assertEqual(response.status_code, 200)

#Testet die put Route
    def test_insert_Item(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "userID": "ABC",
            "productName": "Brot",
            "quantity": "1",
            "quantityUnit": "Stk",
        })
        
        response = self.app.put('/shoppingList', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

#Testet die post Route
    def test_get_oneUserItem(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "userID": "ABC"
        })
        
        response = self.app.post('/shoppingList', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

#Testet die delete Route
    def test_delete_Item(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "userID": "ABC",
            "productName": "Brot"
        })
        
        response = self.app.delete('/shoppingList', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
