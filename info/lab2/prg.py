def f(inpt : str):
    r1,r2,i1,r3,i2,i3,i4 = map(int,list(inpt))
    lst = list(inpt)
    s1 = r1 ^ i1 ^ i2 ^ i4
    s2 = r2 ^ i1 ^ i3 ^ i4
    s3 = r3 ^ i2 ^ i3 ^ i4
    S = s3 * 100 + s2 * 10 + s1
    # print(int(str(S),2)-1)
    bit_err = int(str(S),2)
    ans = ""
    t = 0
    if bit_err == 0:
        return lst[2]+lst[4]+lst[5]+lst[6],"отсутствует"
    for i in [2,4,5,6]:
        if i != int(str(S),2)-1:
            ans += inpt[i]
        else:
            if inpt[i] == "0": ad = "1"
            elif inpt[i] == "1": ad = "0"
            ans += ad
            lst[i] = ad + " ERR"
            t = 1

    return ans,bit_err
try:
    i = input()
    print(f(i)[0])
    print("Ошибка в бите", f(i)[1])
except Exception:
    print("something went wrong :9")