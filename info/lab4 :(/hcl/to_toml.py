# TOML
def to_toml_val(data):
    if type(data) == str:
        return "\"" + data + "\""
    if type(data) == int or type(data) == float:
        return str(data)

def to_toml(data, level=""):
    lines = []
    if type(data) == list:
        for item in data:
            lines.extend(to_toml(item,level))
    else:
        for key, value in data.items():
            if type(value) == dict:
                if level == "":
                    pref = key
                else:
                    pref = level + "." + key
                lines.append(f"[{pref}]")
                lines.extend(to_toml(value, pref))

            elif type(value) == list:
                pref = level + "." + key
                for item in value:
                    lines.append(f"\n[[{pref}]]")
                    lines.extend(to_toml(item, pref))
            else:
                lines.append(f"{key} = {to_toml_val(value)}")

    return lines

def save_toml(data):
    lines = to_toml(data)
    text = "\n".join(lines)
    with open("output.toml", "w", encoding="utf-8") as f:
        f.write(text)