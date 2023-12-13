import PySimpleGUI as sg

from services.AuthService import AuthService

from components.main_gui import MainGUI
from components.register import Register

class Register:
    window = None

    def __init__(self, window):
        self.auth_service = AuthService()
        self.window = window

    @staticmethod
    def build_layout():
        layout = [
            [sg.Text('Email:'), sg.InputText(key='email')],
            [sg.Text('Password:'), sg.InputText(key='password')],
            [sg.Button('Register')]
        ]
        return layout

    def run(self):
        while True:
            event, values = self.window.read()
            if event == sg.WIN_CLOSED:
                break
            elif event == 'Register':
                main_gui = MainGUI()
                main_gui.window.Layout = Register.build_layout()
                main_gui.current_component = Register(main_gui.window)
                main_gui.run()
                return

        self.window.close()