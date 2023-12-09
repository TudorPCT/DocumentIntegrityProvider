from services.AuthService import AuthService


class Register:
    window = None

    def __init__(self, window):
        self.auth_service = AuthService()
        self.window = window

    @staticmethod
    def build_layout():
        pass

    def run(self):
        pass