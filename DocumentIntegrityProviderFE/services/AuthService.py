class AuthService:
    _instance = None

    def __new__(cls, *args, **kwargs):
        if not cls._instance:
            cls._instance = super(AuthService, cls).__new__(cls, *args, **kwargs)
            cls._instance.auth_token = None
        return cls._instance

    def get_auth_token(self):
        return self.auth_token
