# isu = 501145
# v1 = isu % 7
# v2 = isu % 5
# v3 = isu % 3
# print(v1, v2, v3) #  hcl 0 hcl
# python3 -m unittest -v task_1_test.py
from tasks import task_1,task_2,task_3

task = int(input("Введите номер задачи: "))
if task == 1:
    a = input("Введите строку для проверки: ")
    print(task_1.run(a))
elif task == 2:
    a = input("Введите строку для проверки: ")
    print(task_2.run(a))
elif task == 3:
    NUM = input("Введите номер слова для замены: ")
    a = input("Введите строку для проверки: ")
    print(task_3.run(NUM,a))