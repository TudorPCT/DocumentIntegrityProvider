import base64
import json
import os
import requests


class AuthService:
    _instance = None
    auth_token = None

    def __new__(cls, *args, **kwargs):
        if not cls._instance:
            cls._instance = super(AuthService, cls).__new__(cls, *args, **kwargs)
            cls._instance.auth_token = None
        return cls._instance

    def get_auth_token(self):
        return self.auth_token

    def get_user_id(self):
        parts = self.auth_token.split('.')

        encoded_payload = parts[1]

        decoded_payload = base64.urlsafe_b64decode(encoded_payload + '==').decode('utf-8')

        return json.loads(decoded_payload)['sub']

    def login(self, email, password):
        url = os.environ['api_base_link'] + '/api/auth/login'

        headers = {
            "Content-Type": "application/json",
        }

        body = {"email": email, "password": password}

        response = requests.post(url, json=body, headers=headers)

        if response.status_code == 200:
            self.auth_token = response.json()['token']
            print("Login successful.")
        else:
            print(f"Error: {response.status_code}")
