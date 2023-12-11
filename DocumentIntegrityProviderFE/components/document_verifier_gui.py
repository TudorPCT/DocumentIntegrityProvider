import PySimpleGUI as sg
from crpyography.DocumentVerifier import DocumentVerifier

class DocumentVerifierGUI:
    def __init__(self):
        self.verifier = DocumentVerifier()

        layout = [
            [sg.Text("Select Signed Message File:"), sg.Input(key="signed_message_file"), sg.FileBrowse()],
            [sg.Button("Verify Signature"), sg.Button("Exit")],
            [sg.Multiline(key="verification_output", size=(40, 5))]
        ]

        self.window = sg.Window("Document Verifier", layout)

    def run(self):
        while True:
            event, values = self.window.read()

            if event == sg.WINDOW_CLOSED or event == "Exit":
                break

            if event == "Verify Signature":
                signed_message_file = values["signed_message_file"]
                if signed_message_file:
                    with open(signed_message_file, 'rb') as file:
                        signed_message = file.read()
                    self.verifier.verify_signed_message(signed_message)
                    self.window["verification_output"].update("Verification result printed in console.")

        self.window.close()

if __name__ == "__main__":
    app = DocumentVerifierGUI()
    app.run()
