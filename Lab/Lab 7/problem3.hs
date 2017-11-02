import System.IO     
import Data.Char  
    
main = do     
    contents <- readFile "test.txt"     
    writeFile "leet.txt" (leetcode contents) 

leetcode::String->String
leetcode "" = []
leetcode (x:xs)
  | x =='o' = '0' : leetcode(xs)
  | x == 'e' = '3' : leetcode(xs)
  | x == 'a' = '@' : leetcode(xs)
  | x == 'l' = '1' : leetcode(xs)
  | otherwise = x : leetcode(xs)