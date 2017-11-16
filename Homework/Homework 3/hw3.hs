import System.Environment   
import Data.List
import Data.List.Split

main = do
  (file:command:args:xs) <- getArgs
  contents <- readFile file
  if command == "search_last_name" 
  then print ( search_last_name args (makeStudentTree (makeArrayStudent contents)) )
  else print ( search_age (read args) (makeStudentTree (makeArrayStudent contents)) )


{- 
	1. 2pts) Implement a data type called Student. 
	The student type has four fields, first name, last name, major, and age. 
-}

data Student = Student {
  firstName :: String,
  lastName :: String,
  major :: String,
  age :: Int
} deriving (Eq, Show, Read)


{- 
2. 2 pts) Implement a tree node type that has a student as one of its fields.
The tree will be a binary search tree on the attribute age.
-}

--The data type definition
data Tree a = EmptyTree | Node a (Tree a) (Tree a) deriving (Show, Read, Eq) 


-- Create a single Node
makeNode x = Node x EmptyTree EmptyTree  

-- Inserting into a tree 
treeInsert a EmptyTree = makeNode a  
treeInsert a (Node b left right)
  | (age a) == (age b) = Node a left right
  | (age a) < (age b) = Node b (treeInsert a left) right
  | (age a) > (age b) = Node b left (treeInsert a right)


{- 
3. 1 pt) Write a comma separated file in which each line (ended by new line character) 
contains a student, with its fields separated by commas
-}

{- 
4. Write a program that will:

a. 5pts) Read the comma separated file (specified by a command line argument) 
and produce a list of students.

-}
commaToArray x = splitOn "," x

makeStudent [w,x,y,z] = Student {firstName = w, lastName = x, major = y, age = (read z)}

-- 4a
makeArrayStudent input =   
    let allLines = lines input  
        result = map commaToArray allLines
        studentList = map makeStudent result
    in  studentList 

-- 4b
makeStudentTree a = foldr treeInsert EmptyTree a


{- 
  c. 3pts) Write a function that will search through the tree to see if it 
contains a student of age x. It returns true if there is, otherwise it 
returns false.
-}

-- 4c
search_age x EmptyTree = False  
search_age x (Node a left right)  
    | x == (age a) = True  
    | x < (age a)  = search_age x left  
    | x > (age a)  = search_age x right

-- 4d
{- 
  d. 4pts) Write a function that takes a string for last name uses depth-first 
search to find if a student with that name is in the tree.
-}

search_last_name x EmptyTree = []
search_last_name x (Node a left right)
    | x == (lastName a) = "The last name " ++ x ++ " is in the tree."
    | otherwise = search_last_name x left ++ search_last_name x right


