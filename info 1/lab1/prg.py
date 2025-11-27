def to_Fib(a):
    fib = [1,2]
    while fib[-1] + fib[-2] < a:
        fib.append(fib[-1] + fib[-2])

    answer = ""
    fib.reverse()
    for el in fib:
        # print(a,el, a >= el)
        if a >= el:
            a -= el
            answer += "1"
        else:
            answer += "0"
    return answer

try:
    print(to_Fib(int(input())))
except Exception:
    print("Only integers are allowed :(")