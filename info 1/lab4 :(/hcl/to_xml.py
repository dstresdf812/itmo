# XML
def check_inner_type(data):
    for item in data:
        if type(data) == list and type(data[0]) == list:
            return 1
    return 0
def to_xml(data,level=0):
    lines = []
    for key, value in data.items():
        if type(value) == dict:
            lines.append(f"{level * " "}<{key}>")
            lines.extend(to_xml(value, level + 2))
            lines.append(f"{level * " "}</{key}>")
        if type(value) == list and check_inner_type(value) == 0:
            lines.append(f"{level * " "}<{key}>")
            for item in value:
                lines.extend(to_xml(item, level + 2))
            lines.append(f"{level * " "}</{key}>")
        if type(value) == list and check_inner_type(value) == 1:
            for item1 in value:
                lines.append(f"{level * " "}<{key}>")
                for item2 in item1:
                    lines.extend(to_xml(item2, level + 2))
                lines.append(f"{level * " "}</{key}>")
        if type(value) == str or type(value) == int or  type(value) == float:
            lines.append(f"{level * " "}<{key}>{value}</{key}>")

    return lines

def save_xml(data):
    lines = to_xml(data)
    text = "<?xml version='1.0' encoding='UTF-8'?>\n"
    text += "\n".join(lines)
    with open("output.xml", "w", encoding="utf-8") as f:
        f.write(text)