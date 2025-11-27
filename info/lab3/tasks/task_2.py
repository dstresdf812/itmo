# Author = Nedovba Dmitry Andreevich
# Group = P3109
# Date = 20.10.2025

import re

def run(a : str):
    """
    Написать регулярное выражение, которое проверяет корректность email и в качестве
    ответа выдаёт почтовый сервер (почтовый сервер – часть email идущая после «@»).
    Для простоты будем считать, что почтовый адрес может содержать в себе буквы,
    цифры, «.» и «_», а почтовый сервер только буквы и «.». При этом почтовый сервер,
    обязательно должен содержать верхний уровень домена («.ru», «.com», etc.)
    """
    left_part = r"([a-zA-Z]*[_.]*)+"
    right_part = r"[a-zA-Z]+(\.[a-zA-Z]+)"
    reg2 = rf"{left_part}@{right_part}"
    ans = re.fullmatch(reg2, a)
    if ans:
        return ans.group()[ans.group().find("@") + 1:]
    return "Fail!"