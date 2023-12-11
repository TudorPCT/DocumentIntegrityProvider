from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes, serialization
from cryptography.hazmat.primitives.asymmetric import padding

from crpyography.KeyManager import KeyManager


class DigitalSigner:
    def __init__(self):
        self.private_key = KeyManager.retrieve_private_key()

    def generate_signature(self, document):
        document_hash = hashes.Hash(hashes.SHA256(), backend=default_backend())
        document_hash.update(document.encode())
        hashed_document = document_hash.finalize()

        # Sign the hashed document
        signature = self.private_key.sign(
            hashed_document,
            padding.PSS(
                mgf=padding.MGF1(hashes.SHA256()),
                salt_length=padding.PSS.MAX_LENGTH
            ),
            hashes.SHA256()
        )
        return signature
    
    def get_public_key_id(self):
        # Extract the public key in PEM format
        public_key_pem = self.private_key.public_key().public_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PublicFormat.SubjectPublicKeyInfo
        )
        
        # Hash the public key to generate the public key ID
        key_id = hashes.Hash(hashes.SHA256(), backend=default_backend())
        key_id.update(public_key_pem)
        public_key_id = key_id.finalize().hex()

        return public_key_id

    @staticmethod
    def create_signed_message(document, signature, public_key_id):
        signed_message = document + b'\n\nSignature:\n' + signature + b'\n\nPublicKeyId:\n' + public_key_id
        return signed_message
