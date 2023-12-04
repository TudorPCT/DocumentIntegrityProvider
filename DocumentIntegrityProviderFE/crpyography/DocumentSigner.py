from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding

from crpyography.KeyManager import KeyManager


## This class will load a file from the pc and use a private key to digitally sign it
class DigitalSigner:
    def __init__(self):
        self.private_key = KeyManager.retrieve_private_key()

    def generate_signature(self, document):
        signature = self.private_key.sign(
            document,
            padding.PSS(
                mgf=padding.MGF1(hashes.SHA256()),
                salt_length=padding.PSS.MAX_LENGTH
            ),
            hashes.SHA256()
        )
        return signature

    @staticmethod
    def create_signed_message(document, signature, public_key_id):
        signed_message = document + b'\n\nSignature:\n' + signature + b'\n\nPublicKeyId:\n' + public_key_id
        return signed_message
