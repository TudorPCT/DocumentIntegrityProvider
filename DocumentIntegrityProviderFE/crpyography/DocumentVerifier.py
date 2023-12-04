from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding

from crpyography.KeyManager import KeyManager


class DocumentVerifier:
    @staticmethod
    def extract_document_and_signature(signed_message):
        document, details = signed_message.split(b'\n\nSignature:\n', 1)
        signature, public_key_id = details.split(b'\n\nPublicKeyId:\n', 1)
        return document, signature, public_key_id

    @staticmethod
    def verify_signed_message(signed_message):
        document, signature, public_key_id = DocumentVerifier.extract_document_and_signature(signed_message)

        public_key = KeyManager.retrieve_public_key(public_key_id)

        if public_key is None:
            return
        
        document_hash = hashes.Hash(hashes.SHA256(), backend=default_backend())
        document_hash.update(document.encode())
        hashed_document = document_hash.finalize()

        try:
            public_key.verify(
                signature,
                hashed_document,
                padding.PSS(
                    mgf=padding.MGF1(hashes.SHA256()),
                    salt_length=padding.PSS.MAX_LENGTH
                ),
                hashes.SHA256()
            )
            print("Signature is valid.")
        except Exception as e:
            print("Signature is invalid:", e)
