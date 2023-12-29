import requests

from components.login import Login
from components.main_gui import MainGUI


def auth_monitor(func):
    def wrapper(*args, **kwargs):
        try:
            return func(*args, **kwargs)
        except requests.exceptions.HTTPError as err:
            if err.response.status_code == 401:
                main_gui = MainGUI()
                main_gui.window.Layout = Login.build_layout()
                main_gui.current_component = Login(main_gui.window)
                main_gui.run()
                return
            raise err

    return wrapper
