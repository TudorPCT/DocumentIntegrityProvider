import PySimpleGUI as sg

import jsonify as jsonify
from services.AuthService import AuthService


class AuthInterceptor:
    def __init__(self):
        self.auth_service = AuthService()

    def intercept(self, request):
        authorization_header = request.headers.get('Authorization')

        if authorization_header:
            token_parts = authorization_header.split()

            if len(token_parts) == 2 and token_parts[0].lower() == 'bearer':
                token = token_parts[1]

                user_id = self.auth_service.verify_token(token)

                if user_id:
                    return True
                else:
                    sg.popup('Invalid or expired token. Please log in again.', title='Error')
                    return jsonify({'error': 'Invalid or expired token'}), 401
            else:
                sg.popup('Invalid token format. Please provide a valid Bearer token.', title='Error')
                return jsonify({'error': 'Invalid token format'}), 401
        else:
            sg.popup('Token not provided. Please log in.', title='Error')
            return jsonify({'error': 'Token not provided'}), 401
