import PySimpleGUI as sg
from crpyography.DocumentSigner import DocumentSigner
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes

class DocumentSignerGUI:
    def __init__(self):
        # self.signer = DocumentSigner()

        sg.theme('Default1')
        sg.set_options(font=('Helvetica', 15))

        layout = [
            [sg.Text("Select Document File:"), sg.Input(key="document_file"), sg.FileBrowse()],
            [sg.Button("Sign Document"), sg.Button("Exit")],
            [sg.Multiline(key="document_output", size=(40, 5))],
            [sg.Button("Go To Verify Signature")]
        ]

        self.window = sg.Window("Document Signer", layout, size=(500, 300))

    def run(self):
        from components.document_verifier_gui import DocumentVerifierGUI

        while True:
            event, values = self.window.read()

            if event == sg.WINDOW_CLOSED or event == "Exit":
                break

            if event == "Sign Document":
                document_file = values["document_file"]

                if document_file:
                    # self.signer.sign_document(document_file)
                    pass
                        
            if event == "Go To Verify Signature":
                self.window.hide()
                verifier_gui = DocumentVerifierGUI()
                verifier_gui.run()
                self.window.close()

        self.window.close()


if __name__ == "__main__":
    app = DocumentSignerGUI()
    app.run()
