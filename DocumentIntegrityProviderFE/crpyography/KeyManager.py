import os
import requests
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import rsa

from services.AuthService import AuthService


class KeyManager:

    @staticmethod
    def generate_keys():
        auth_service = AuthService()

        private_key = rsa.generate_private_key(
            public_exponent=65537,
            key_size=2048,
            backend=default_backend()
        )
        public_key = private_key.public_key()

        KeyManager.save_keys(private_key, public_key, auth_service.get_auth_token())

    @staticmethod
    def save_keys(private_key, public_key, token):
        KeyManager.save_private_key(private_key)
        KeyManager.save_public_key(public_key, token)

    @staticmethod
    def save_private_key(private_key):
        with open("pk.pem", "wb") as key_file:
            key_file.write(
                private_key.private_bytes(
                    encoding=serialization.Encoding.PEM,
                    format=serialization.PrivateFormat.PKCS8,
                    encryption_algorithm=serialization.NoEncryption()
                )
            )

    @staticmethod
    def save_public_key(public_key, token):
        public_key_pem = public_key.public_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PublicFormat.SubjectPublicKeyInfo
        )
        public_key_str = public_key_pem.decode('utf-8')

        url = os.environ['api_base_link'] + '/api/public-key'

        headers = {
            "Authorization": "Bearer" + token,
            "Content-Type": "application/json",
        }

        body = {"publicKey": public_key_str}

        response = requests.post(url, json=body, headers=headers)

        if response.status_code == 200:
            print("Public key sent successfully.")
        else:
            print(f"Error: {response.status_code}")

    @staticmethod
    def retrieve_private_key():
        with open("pk.pem", "rb") as key_file:
            private_key = serialization.load_pem_private_key(
                key_file.read(),
                password=None,
                backend=default_backend()
            )
        return private_key

    @staticmethod
    def retrieve_public_key(public_key_id):
        try:
            url = os.environ['api_base_link'] + '/api/public-key/' + public_key_id

            response = requests.get(url)

            if response.status_code == 200:
                public_key_pem = response.json().get("publicKey")
                if public_key_pem:
                    public_key = serialization.load_pem_public_key(
                        public_key_pem.encode('utf-8'),
                        backend=default_backend()
                    )
                    return public_key
                else:
                    print("Public key not found in the JSON response.")
                    return None
            else:
                print(f"Error retrieving public key. Status code: {response.status_code}")
                return None
        except Exception as e:
            print(f"Error loading public key: {e}")
        return None
