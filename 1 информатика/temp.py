dots = {"A":(0,0,0),
        "B":(1,2,3),
        "C":(3,2,1),
        "D":(2,3,1),
}

P = (2.5,2.5,1)

def getPlaneEquation(d1,d2,d3,d4,P):
    V1 = (d2[0] - d1[0],d2[1] - d1[1],d2[2] - d1[2])
    V2 = (d3[0] - d1[0], d3[1] - d1[1], d3[2] - d1[2])
    equation = [V1[1]*V2[2]-V2[1]*V1[2],
                V1[2]*V2[0]-V2[2]*V1[0],
                V1[0]*V2[1]-V2[0]*V1[1]]
    D = -(d1[0]*equation[0]+d1[1]*equation[1]+d1[2]*equation[2])
    equation.append(D)
    return checkDot(checkEquation(equation,d4),P,d4)

def checkEquation(equation,d4):
    x,y,z = d4
    A,B,C,D = equation
    if A*x + B*y + C*z + D < 0:
        return [-el for el in equation]
    return equation

def checkDot(equation,P,d4):
    x1,y1,z1 = P
    x2,y2,z2 = d4
    A, B, C, D = equation
    if (A*x1 + B*y1 + C*z1 + D) > 0 and (A*x2 + B*y2 + C*z2 + D) > 0 :
        return 1
    if (A*x1 + B*y1 + C*z1 + D) == 0:
        return 2
    return 0

def getVal(edges):
    dots = ["A","B","C","D"]
    if len(edges) == 3:
        for dot in dots:
            if all(dot in val for val in edges):
                return dot
    if len(edges) == 2:
        s = ""
        for dot in dots:
            if all(dot in val for val in edges):
                s += dot
                dots.remove(dot)
        for dot in dots:
            if all(dot in val for val in edges):
                s += dot
        return s
    if len(edges) == 1:
        return edges[0]

def run(dots):
    ABC = getPlaneEquation(dots["A"], dots["B"], dots["C"], dots["D"], P)
    ABD = getPlaneEquation(dots["A"], dots["B"], dots["D"], dots["C"], P)
    ACD = getPlaneEquation(dots["A"], dots["C"], dots["D"], dots["B"], P)
    BCD = getPlaneEquation(dots["B"], dots["C"], dots["D"], dots["A"], P)
    results = {"ABC":ABC, "ABD":ABD, "ACD":ACD, "BCD":BCD}
    if any(x==0 for x in results.values()):
        print("Точка P лежит вне тетраэдра")
    elif all(x==1 for x in results.values()):
        print("Точка P лежит внутри тетраэдра")
    else:
        edges = []
        for key,val in results.items():
            if val == 2:
                edges.append(key)

        if len(edges) == 3:
            s = f"Точка P совпадает с вершиной {getVal(edges)}"
        if len(edges) == 2:
            s = f"Точка P лежит на пересечении граней {edges[0]} и {edges[1]}, то есть на ребре {getVal(edges)}"
        if len(edges) == 1:
            s = f"Точка P лежит на грани {getVal(edges)}"
        print(s)

run(dots)