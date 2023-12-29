import PySimpleGUI as sg

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
            [sg.Text('Password:'), sg.InputText(key='password')],
            [sg.Text('', font=('Helvetica', 7))],
            [sg.Text('Confirm Password:', key='confirm_password-label', visible=False),
             sg.InputText(key='confirm_password', visible=False)],
            [sg.Text('', font=('Helvetica', 10))],
            [sg.Button('Login'), sg.Button('Register')]
        ]
        return layout

    def run(self):
        while True:
            event, values = self.window.read()
            if event == sg.WIN_CLOSED:
                break
            elif event == 'Login':
                from components.document_signer_gui import DocumentSignerGUI
                self.auth_service.login(values['email'], values['password'])
                sg.popup('Login successful!', title='Success')
                app = DocumentSignerGUI()
                app.run()
            elif event == 'Register':
                from components.register import Register
                main_gui = MainGUI()
                self.window['title'].update(value='Register')
                self.window['confirm_password-label'].update(visible=True)
                self.window['confirm_password'].update(visible=True)
                main_gui.current_component = Register(main_gui.window)
                main_gui.run()
                return

        self.window.close()
