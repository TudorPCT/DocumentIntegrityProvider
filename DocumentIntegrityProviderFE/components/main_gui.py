import PySimpleGUI as sg


class MainGUI:
    window = None
    current_component = None
    _instance = None

    def __new__(cls, *args, **kwargs):
        if not cls._instance:
            from components.login import Login
            cls._instance = super(MainGUI, cls).__new__(cls, *args, **kwargs)
            sg.theme('Default1')
            sg.set_options(font=('Helvetica', 15))
            cls._instance.window = sg.Window('Document Integrity Provider', Login.build_layout(), size=(500, 300))
            cls._instance.current_component = Login(cls._instance.window)
        return cls._instance

    def run(self):
        self.current_component.run()
