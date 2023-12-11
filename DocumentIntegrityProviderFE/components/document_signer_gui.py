import PySimpleGUI as sg
from crpyography.DocumentSigner import DigitalSigner
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes

class DocumentSignerGUI:
    def __init__(self):
        self.signer = DigitalSigner()

        layout = [
            [sg.Text("Select Document File:"), sg.Input(key="document_file"), sg.FileBrowse()],
            [sg.Button("Sign Document"), sg.Button("Exit")],
            [sg.Multiline(key="document_output", size=(40, 5))]
        ]

        self.window = sg.Window("Document Signer", layout)

    def run(self):
        while True:
            event, values = self.window.read()

            if event == sg.WINDOW_CLOSED or event == "Exit":
                break

            if event == "Sign Document":
                document_file = values["document_file"]
                if document_file:
                    with open(document_file, 'r') as file:
                        document_content = file.read()
                    signature = self.signer.generate_signature(document_content)
                    public_key_id = self.signer.get_public_key_id()
                    signed_message = self.signer.create_signed_message(document_content.encode(), signature, public_key_id)
                    self.window["document_output"].update(f"Document signed successfully!\nSigned Message:\n{signed_message.hex()}")

        self.window.close()

if __name__ == "__main__":
    app = DocumentSignerGUI()
    app.run()
