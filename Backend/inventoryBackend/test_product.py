import unittest
import json
from app import app

class TestFlaskRoutes(unittest.TestCase):
    def setUp(self):
        self.app = app.test_client()
        self.app.testing = True 

    def test_get_oneProduct(self):
        mock_request_headers = {'Content-Type': 'application/json'}
        mock_request_data = json.dumps({
            "barcode": "4103840026417"  
        })

        response = self.app.post('/product', headers=mock_request_headers, data=mock_request_data)
        self.assertEqual(response.status_code, 200)

        data = response.get_json()

        self.assertEqual(data["productcode"], 4103840026417)
        self.assertEqual(data["carbohydrates"], 4)
        self.assertEqual(data["energy-kcal"], 69)
        self.assertEqual(data["fat"], 1)
        self.assertEqual(data["nutriscore"], "z")
        self.assertEqual(data["protein"], 12)
        self.assertEqual(data["sugar"], 3)
        self.assertEqual(data["name"], "High Protein Quark Creme - Pfirsich Maracuja")
        self.assertEqual(data["pictureLink"], "https://images.openfoodfacts.org/images/products/410/384/002/6417/front_de.3.400.jpg")

if __name__ == '__main__':
    unittest.main()
