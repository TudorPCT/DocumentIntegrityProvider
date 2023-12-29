def read_file(file_path):
    with open(file_path, "rb") as file:
        content = file.read()
    return content


def write_signature_to_file(signature, output_file):
    with open(output_file, "wb") as file:
        file.write(signature)
