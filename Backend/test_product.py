import json
import unittest
from product import app

class FlaskTestCase(unittest.TestCase):

    def setUp(self):
        self.app = app.test_client()

    def test_add_data_existing_product(self):

        product_data = {
            "barcode": "4103840026417"
        }

        response = self.app.post(
            "/product", 
            data=json.dumps(product_data), 
            content_type='application/json'
        )

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.data)
        self.assertEqual(data["productcode"], 4103840026417)


if __name__ == "__main__":
    unittest.main()
