import unittest
import task_1

class Task1Test(unittest.TestCase):
    def test_one(self):
        a = "I don't know what to write here"
        self.assertEqual(task_1.run(a), a)

    def test_two(self):
        a = "I don't know what what what to write here"
        res = "I don't know what to write here"
        self.assertEqual(task_1.run(a), res)

    def test_three(self):
        a = "I don't know what, what to write here"
        self.assertEqual(task_1.run(a), a)

    def test_four(self):
        a = "I don't know         know what to write here"
        res = "I don't know what to write here"
        self.assertEqual(task_1.run(a), res)

    def test_five(self):
        a = "I don't know knowledge to write here"
        res = "I don't know knowledge to write here"
        self.assertEqual(task_1.run(a), res)

if __name__ == '__main__':
    unittest.main()