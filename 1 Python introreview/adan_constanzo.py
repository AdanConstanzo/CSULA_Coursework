
# list comprehensions

def evens(list):
    evn_numbrs = [x for x in list if x % 2 == 0]
    return evn_numbrs

evens(a)


# bull and cow

cow = 0
bull = 0
s = [1,0,3,0]

u = [3,0,1,1]


for x in range(4):
    if s[x] == u[x]:
        bull += 1
        u[x] = None
        s[x] = None

for x in u:
    if x in s and x != None:
        s.remove(x)
        

print("%i bull"%bull)
print("%i cows"%(len(u) - len(s)))


# tic tac toe

def vertical(number):
    return " ---" * number
def horizontal(number):
    return "|   " * (number + 1)
    
def printBoard(x):
    for _ in range(x):
        print(vertical(x))
        print(horizontal(x))
    print(vertical(x))
printBoard(4)