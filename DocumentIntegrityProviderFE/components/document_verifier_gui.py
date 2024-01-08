import os
import requests
import PySimpleGUI as sg
from crpyography.DocumentVerifier import DocumentVerifier
from components.document_signer_gui import DocumentSignerGUI


class DocumentVerifierGUI:
    def __init__(self):
        self.verifier = DocumentVerifier()

        sg.theme('Default1')
        sg.set_options(font=('Helvetica', 15))

        layout = [
            [sg.Text("Select Signed Message File:"), sg.Input(key="signed_message_file"), sg.FileBrowse()],
            [sg.Button("Verify Signature"), sg.Button("Exit")],
            [sg.Multiline(key="verification_output", size=(40, 5))],
            [sg.Button("Go To Sign Document")]
        ]

        self.window = sg.Window("Document Verifier", layout, size=(500, 300))

    def run(self):
        while True:
            event, values = self.window.read()

            if event == sg.WINDOW_CLOSED or event == "Exit":
                break

            if event == "Verify Signature":
                signed_message_file = values["signed_message_file"]
                if signed_message_file:
                    userId = self.verifier.verify_signed_document(signed_message_file)
                    self.window["verification_output"].update("Verification result printed in console.")

                    if userId:
                        email_response = self.get_user_email_by_id(userId)
                        if email_response.status_code == 200:
                            email = email_response.text
                            self.window["verification_output"].update(f"User ID: {userId}, Email: {email}")
                        else:
                            self.window["verification_output"].update(email_response.status_code)


            if event == "Go To Sign Document":
                self.window.hide()
                signer_gui = DocumentSignerGUI()
                signer_gui.run()
                self.window.close()

        self.window.close()


    def get_user_email_by_id(self, user_id):
        url = os.environ['api_base_link'] + f"/api/users/{user_id}/email"
        response = requests.get(url)
        return response

if __name__ == "__main__":
    app = DocumentVerifierGUI()
    app.run()
