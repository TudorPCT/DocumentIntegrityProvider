import requests.hooks

from components.login import Login
from components.main_gui import MainGUI
from services.AuthService import AuthService


def auth_interceptor(func):
    def wrapper(*args, **kwargs):
        try:
            auth_service = AuthService()
            session = requests.Session()
            if auth_service.get_auth_token() is not None:
                headers = {'Authorization': 'Bearer ' + auth_service.get_auth_token()}
                session.headers.update(headers)
            return func(*args, **kwargs, session=session)
        except requests.exceptions.HTTPError as err:
            if err.response.status_code == 401:
                main_gui = MainGUI()
                main_gui.window.Layout = Login.build_layout()
                main_gui.current_component = Login(main_gui.window)
                main_gui.run()
                return
            raise err

    return wrapper
