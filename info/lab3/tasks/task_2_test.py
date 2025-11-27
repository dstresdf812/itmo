import unittest
import task_2

class Task2Test(unittest.TestCase):
    def test_one(self):
        a = "student@itmo.ru"
        res = "itmo.ru"
        self.assertEqual(task_2.run(a), res)

    def test_two(self):
        a = "student@i.t.m.o.ru"
        res = "Fail!"
        print(task_2.run(a))
        self.assertEqual(task_2.run(a), res)

    def test_three(self):
        a = "student@itmo.ru@itmo.ru"
        res = "Fail!"
        self.assertEqual(task_2.run(a), res)

    def test_four(self):
        a = "student"
        res = "Fail!"
        self.assertEqual(task_2.run(a), res)

    def test_five(self):
        a = "student@itmo"
        res = "Fail!"
        self.assertEqual(task_2.run(a), res)

if __name__ == '__main__':
    unittest.main()