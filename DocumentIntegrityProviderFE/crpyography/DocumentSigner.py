from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.primitives.serialization import Encoding, PublicFormat
from cryptography.hazmat.primitives.asymmetric import ec
from cryptography.hazmat.primitives.asymmetric.utils import decode_dss_signature, encode_dss_signature
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives import asymmetric
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import utils


## This class will load a file from the pc and use a private key to digitally sign it
class DigitalSigner:
    def __init__(self):
        self.private_key = KeyManager.load_private_key()

    def sign_document(self, document):
        signature = self.private_key.sign(
            document,
            padding.PSS(
                mgf=padding.MGF1(hashes.SHA256()),
                salt_length=padding.PSS.MAX_LENGTH
            ),
            hashes.SHA256()
        )
        return signature

    def sign_document(self, document):
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
    def create_signed_message(document, signature):
        signed_message = document + b'\n\nSignature:\n' + signature
        return signed_message

    @staticmethod
    def extract_document_and_signature(signed_message):
        document, signature = signed_message.split(b'\n\nSignature:\n', 1)
        return document, signature

    def verify_signed_message(self, signed_message):
        document, signature = self.extract_document_and_signature(signed_message)

        # Verify the signature using the public key
        try:
            self.public_key.verify(
                signature,
                document,
                padding.PSS(
                    mgf=padding.MGF1(hashes.SHA256()),
                    salt_length=padding.PSS.MAX_LENGTH
                ),
                hashes.SHA256()
            )
            print("Signature is valid.")
        except Exception as e:
            print("Signature is invalid:", e)
