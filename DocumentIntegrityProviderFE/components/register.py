import PySimpleGUI as sg
import re


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

    @staticmethod
    def validate_email(email):
        return re.search(r'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$', email)

    @staticmethod
    def validate_password(password):
        return re.search(r'^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$', password)

    def run(self):
        while True:
            event, values = self.window.read()
            if event == sg.WIN_CLOSED:
                break
            elif event == 'Register':
                if not self.validate_email(values['email']):
                    sg.popup('Invalid email format!', title='Error')
                    continue
                elif not self.validate_password(values['password']):
                    sg.popup('Invalid password format!\n' 
                             'Password must be at least 8 characters long, contain at least '
                             'one uppercase letter, one lowercase letter, one digit and one '
                             'special character', title='Error')
                    continue
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

