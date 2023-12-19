import os

import requests

from crpyography.KeyManager import KeyManager
from services.AuthService import AuthService


class RegisterService:

    @staticmethod
    def register(email, password):
        url = os.environ['api_base_link'] + '/api/auth/register'

        headers = {
            "Content-Type": "application/json",
        }

        body = {"email": email, "password": password}

        response = requests.post(url, json=body, headers=headers)

        if response.status_code == 200:
            print("Registration successful.")
            AuthService().login(email, password)
            KeyManager.generate_keys()
        else:
            print(f"Error: {response.status_code}")
