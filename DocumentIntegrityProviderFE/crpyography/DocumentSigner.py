import os

from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding

from crpyography.KeyManager import KeyManager
from crpyography.Util import read_file, write_signature_to_file
from services.AuthService import AuthService


class DocumentSigner:
    def __init__(self):
        self.private_key = KeyManager.retrieve_private_key()

    def generate_signature(self, document):
        document_hash = hashes.Hash(hashes.SHA256(), backend=default_backend())
        document_hash.update(document)
        hashed_document = document_hash.finalize()

        signature = self.private_key.sign(
            hashed_document,
            padding.PSS(
                mgf=padding.MGF1(hashes.SHA256()),
                salt_length=padding.PSS.MAX_LENGTH
            ),
            hashes.SHA256()
        )
        return signature

    def create_signed_message(self, document, signature, user_id):
        signed_message = document + b'\n\nSignature:\n' + signature + b'\n\nUserId:\n' + user_id.encode()
        return signed_message

    def sign_document(self, document_path):
        auth_service = AuthService()
        document = read_file(document_path)
        signature = self.generate_signature(document)
        signed_message = self.create_signed_message(document, signature, auth_service.get_user_id())

        directory, base_name = os.path.split(document_path)
        name, extension = os.path.splitext(base_name)
        modified_name = name + '-signed' + extension
        new_document_path = os.path.join(directory, modified_name)
        write_signature_to_file(signed_message, new_document_path)
        return True
