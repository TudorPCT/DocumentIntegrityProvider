import PySimpleGUI as sg
import re

from components.main_gui import MainGUI
from services.AuthService import AuthService


class Login:
    window = None

    def __init__(self, window):
        self.auth_service = AuthService()
        self.window = window

    @staticmethod
    def build_layout():
        layout = [
            [sg.Text('Login', font=('Helvetica', 25), key='title')],
            [sg.Text('', font=('Helvetica', 10))],
            [sg.Text('Email:       '), sg.InputText(key='email')],
            [sg.Text('', font=('Helvetica', 7))],
            [sg.Text('Password:'), sg.InputText(key='password', password_char="*")],
            [sg.Text('', font=('Helvetica', 7))],
            [sg.Text('Confirm Password:', key='confirm_password-label', visible=False),
             sg.InputText(key='confirm_password', password_char="*", visible=False)],
            [sg.Text('', font=('Helvetica', 10))],
            [sg.Button('Login'), sg.Button('Register')]
        ]
        return layout

    @staticmethod
    def validate_email(email):
        return re.search(r'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$', email)

    @staticmethod
    def validate_password(password):
        return re.search(r'^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$', password)

    # def on_typing(self, event, value):
    #     if event == 'email':
    #         if not self.validate_email(value):
    #             self.window['email'].update('Invalid email format', text_color='red')
    #             self.window['email'].Widget.config(bg='blue')
    #         else:
    #             self.window['email'].update('')
    #             self.window['email'].Widget.config(bg='white')
    #     elif event == 'password':
    #         if not self.validate_password(value):
    #             self.window['password'].update('Password must be at least 8 characters long, contain at least '
    #                                                  'one uppercase letter, one lowercase letter, one digit and one '
    #                                                  'special character', text_color='red')
    #             self.window['password'].Widget.config(bg='blue')
    #         else:
    #             self.window['password'].update('')
    #             self.window['password'].Widget.config(bg='white')

    def run(self):
        while True:
            event, values = self.window.read()
            if event == sg.WIN_CLOSED:
                break
            elif event == 'Login':
                if not self.validate_email(values['email']):
                    sg.popup('Invalid email format!', title='Error')
                    continue
                elif not self.validate_password(values['password']):
                    sg.popup('Invalid password format!\n' 
                             'Password must be at least 8 characters long, contain at least '
                             'one uppercase letter, one lowercase letter, one digit and one '
                             'special character', title='Error')
                    continue
                from components.document_signer_gui import DocumentSignerGUI
                self.auth_service.login(values['email'], values['password'])
                sg.popup('Login successful!', title='Success')
                self.window.close()
                app = DocumentSignerGUI()
                app.run()
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
                from components.register import Register
                main_gui = MainGUI()
                self.window['title'].update(value='Register')
                self.window['confirm_password-label'].update(visible=True)
                self.window['confirm_password'].update(visible=True)
                main_gui.current_component = Register(main_gui.window)
                main_gui.run()
                self.window.close()
                return
            else:
                self.on_typing(event, values[event])

        self.window.close()
