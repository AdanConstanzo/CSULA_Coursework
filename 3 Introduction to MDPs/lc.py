'''
	Count occurrences in a sequence
'''
def counted(x, lst):

	return len([y for y in lst if x == y])

# print(counted(4, [9, 24, 4, 6, 12, 34, 17, 4, 3, 25, 30, 2, 18, 2])) 

'''
	Show element in context. (If at the start or end of list, use $.)
'''
def surrounded(x, lst):
	
	lst.insert(0, '$')
	lst.append('$')
	return [(lst[y-1],lst[y],lst[y+1]) for y in range(len(lst)) if lst[y]==x]

# print(surrounded('a', list('abdcaegbahydaa')))


'''
	Print dictionary of occurrence positions of elements divisible by x and their 
	quotients.
'''

def indexed_multiples(x, lst):
	return {i: (lst[i], lst[i]//x) for i in range(len(lst)) if lst[i]%x  == 0}

# print(indexed_multiples(6, [9, 24, 4, 6, 12, 34, 17, 3, 25, 30, 2, 18, 2]))








