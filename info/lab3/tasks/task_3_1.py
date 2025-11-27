# Author = Nedovba Dmitry Andreevich
# Group = P3109
# Date = 20.10.2025

import re
import inspect
import pymorphy2

def XD(obj):
    s = obj.group(0)
    global changeTo
    if s.istitle():
        return changeTo.capitalize()
    else:
        return changeTo.lower()

def run(NUM:int,a : str):
    global changeTo
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
    #print(change)
    #print(repeated)
    working_With = []
    for (to,what) in change:
        reg = morph.parse(to)[0].normal_form[:-3]
        reg = rf'({reg}.*?)\b'
        #print(reg,re.findall(reg,a,flags=re.IGNORECASE))
        changeTo = to
        a = re.sub(reg, XD, a,flags=re.IGNORECASE)
    return a

print(run(2,"Футбольный клуб «Реал Мадрид» является 15-кратным обладателем главного \
футбольного европейского трофея – Лиги Чемпионов. Данный турнир организован \
Союзом европейских футбольных ассоциаций (УЕФА). Идея о континентальном \
футбольном турнире пришла к журналисту Габриэлю Ано в 1955 году."))

print(run(2,"Маленький кот видит маленького щенка."))

print(run(1,"Большой дуб и большое дерево возле большого дома."))

print(run(3,"Красивая птица летела над красивым морем и красивой скалой."))
print(run(2,"Добрый учитель и умный ученик помогли доброй девушке и умной подруге."))