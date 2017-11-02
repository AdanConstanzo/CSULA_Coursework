--2. 4pts: Write a Haskell program named leetcode. It asks for a phrase, and it converts all 
--lowercase o's to 0, all lowercase e's to 3, all lowercase a's to @, and all lowercase l's to 1, 
--and prints the result to the screen.


main = do  
    putStrLn "Enter your phrase"  
    phrase <- getLine  
    putStrLn (leetcode phrase) 

leetcode::String->String
leetcode "" = []
leetcode (x:xs)
  | x =='o' = '0' : leetcode(xs)
  | x == 'e' = '3' : leetcode(xs)
  | x == 'a' = '@' : leetcode(xs)
  | x == 'l' = '1' : leetcode(xs)
  | otherwise = x : leetcode(xs)