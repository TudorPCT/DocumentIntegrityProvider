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
