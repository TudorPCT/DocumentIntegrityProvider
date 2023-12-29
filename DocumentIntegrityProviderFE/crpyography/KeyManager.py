import os
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import rsa

from interceptors.auth_interceptor import auth_interceptor
from interceptors.auth_monitor import auth_monitor


class KeyManager:

    @staticmethod
    def generate_keys():

        private_key = rsa.generate_private_key(
            public_exponent=65537,
            key_size=2048,
            backend=default_backend()
        )
        public_key = private_key.public_key()

        KeyManager.save_keys(private_key, public_key)

    @staticmethod
    def save_keys(private_key, public_key):
        KeyManager.save_private_key(private_key)
        KeyManager.save_public_key(public_key)

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
    @auth_monitor
    @auth_interceptor
    def save_public_key(public_key, session):
        public_key_pem = public_key.public_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PublicFormat.SubjectPublicKeyInfo
        )
        public_key_str = public_key_pem.decode('utf-8')

        url = os.environ['api_base_link'] + '/api/public-key'

        headers = {
            "Content-Type": "application/json",
        }

        session.headers.update(headers)

        body = {"publicKey": public_key_str}

        response = session.post(url, json=body)

        if response.status_code == 200:
            print("Public key sent successfully.")

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
    @auth_monitor
    @auth_interceptor
    def retrieve_public_key(user_id, session):
        url = os.environ['api_base_link'] + '/api/public-key/' + user_id.decode('utf-8')

        response = session.get(url)

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
