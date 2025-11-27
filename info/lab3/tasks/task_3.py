# Author = Nedovba Dmitry Andreevich
# Group = P3109
# Date = 20.10.2025

import re
import inspect
import pymorphy2

def run(NUM:int,a : str):
    """
    Вам необходимо поменять падежи в тексте у прилагательных, которые встречаются
    несколько раз. На вход подаётся текст и порядковый номер слова, падежная форма
    которого будет использована для замены.
    """
    from collections import namedtuple
    ArgSpec = namedtuple('ArgSpec', 'args varargs keywords defaults')
    def getargspec(func):
        return ArgSpec(*inspect.getfullargspec(func)[:4])
    inspect.getargspec = getargspec

    words = [x.group() for x in re.finditer(r"\b\w+\b",a)]
    morph = pymorphy2.MorphAnalyzer()
    repeated = {}
    change = []
    for word in words:
        rs = morph.parse(word)[0][1]
        if str(rs)[:4] == "ADJF":
            repeated[morph.parse(word)[0].normal_form] = repeated.get(morph.parse(word)[0].normal_form, 0) + 1

            if repeated[morph.parse(word)[0].normal_form] == NUM:
                change.append((word,morph.parse(word)[0].normal_form))
    for word in words:
        rs = morph.parse(word)[0].normal_form
        for x in change:
            if rs == x[1]:
                if word[0].isupper():
                    a = a.replace(word,x[0].capitalize())
                else:
                    a = a.replace(word,x[0].lower())

    return a

print(run(2,"Футбольный клуб «Реал Мадрид» является 15-кратным обладателем главного\
футбольного европейского трофея – Лиги Чемпионов. Данный турнир организован\
Союзом европейских футбольных ассоциаций (УЕФА). Идея о континентальном\
футбольном турнире пришла к журналисту Габриэлю Ано в 1955 году."))