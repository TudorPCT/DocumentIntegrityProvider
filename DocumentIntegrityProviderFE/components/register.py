import PySimpleGUI as sg

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
    def build_layout():
        layout = [
            [sg.Text('Email:'), sg.InputText(key='email')],
            [sg.Text('Password:'), sg.InputText(key='password')],
            [sg.Text('Confirm Password:'), sg.InputText(key='confirm_password')],
            [sg.Button('Register'), sg.Button('Back to Login')]
        ]
        return layout

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
            elif event == 'Back to Login':
                from components.login import Login
                main_gui = MainGUI()
                main_gui.window.Layout = Login.build_layout()
                main_gui.current_component = Login(main_gui.window)
                main_gui.run()
                return

        self.window.close()

