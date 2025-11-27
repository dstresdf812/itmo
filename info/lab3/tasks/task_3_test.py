import unittest
import task_3_1

class Task3Test(unittest.TestCase):
    def test_one(self):
        a = "Красивый дом стоит рядом с красивым садом."
        NUM = 2
        res = "Красивым дом стоит рядом с красивым садом."
        self.assertEqual(task_3_1.run(NUM,a), res)

    def test_two(self):
        a = "Большой дуб и большое дерево возле большого дома."
        NUM = 1
        res = "Большой дуб и большой дерево возле большой дома."

        self.assertEqual(task_3_1.run(NUM,a), res)

    def test_three(self):
        a = "Маленький кот видит маленького щенка."
        NUM = 2
        res = "Маленького кот видит маленького щенка."

        self.assertEqual(task_3_1.run(NUM,a), res)

    def test_four(self):
        a = "Красивая птица летела над красивым морем и красивой скалой."
        NUM = 3
        res = "Красивой птица летела над красивой морем и красивой скалой."

        self.assertEqual(task_3_1.run(NUM,a), res)

    def test_five(self):
        a = "Добрый учитель и умный ученик помогли доброй девушке и умной подруге."
        NUM = 2
        res = "Доброй учитель и умной ученик помогли доброй девушке и умной подруге."
        self.assertEqual(task_3_1.run(NUM,a), res)

if __name__ == '__main__':
    unittest.main()