import json
import unittest
from product import app

class FlaskTestCase(unittest.TestCase):

    def setUp(self):
        self.app = app.test_client()

    def test_add_data_new_product(self):
        
        # Testfall, wenn Produkt nicht in der Datenbank existiert
        product_data = {
            "barcode": "42143574"
        }

        response = self.app.post(
            "/product", 
            data=json.dumps(product_data), 
            content_type='application/json'
        )

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.data)
        self.assertEqual(data["productcode"], 42143574)


    def test_add_data_existing_product(self):

        # Testfall, wenn Produkt bereits in der Datenbank existiert
        product_data = {
            "barcode": "5000159452540"
        }

        response = self.app.post(
            "/product", 
            data=json.dumps(product_data), 
            content_type='application/json'
        )

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.data)
        self.assertEqual(data["productcode"], 5000159452540)


if __name__ == "__main__":
    unittest.main()
