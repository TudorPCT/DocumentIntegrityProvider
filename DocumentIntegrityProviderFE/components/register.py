import PySimpleGUI as sg

from components.document_signer_gui import DocumentSignerGUI
from services.AuthService import AuthService
from services.RegisterService import RegisterService

from components.main_gui import MainGUI


class Register:
    window = None

    def __init__(self, window):
        self.register_service = RegisterService()
        self.auth_service = AuthService()
        self.window = window

    def run(self):
        while True:
            event, values = self.window.read()
            if event == sg.WIN_CLOSED:
                break
            elif event == 'Register':
                if values['password'] != values['confirm_password']:
                    sg.popup('Passwords do not match!', title='Error')
                else:
                    self.register_service.register(values['email'], values['password'])
                    sg.popup('Registration successful!', title='Success')
                    self.window.close()
                    app = DocumentSignerGUI()
                    app.run()
            elif event == 'Login':
                from components.login import Login
                main_gui = MainGUI()
                self.window['title'].update(value='Login')
                self.window['confirm_password-label'].update(visible=False)
                self.window['confirm_password'].update(visible=False)
                main_gui.current_component = Login(main_gui.window)
                main_gui.run()
                return

        self.window.close()

