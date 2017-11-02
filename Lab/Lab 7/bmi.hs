main = do  
    putStrLn "Hello, How tall are you?"  
    height <- getLine  
    putStrLn "How much do you weight?"  
    weight<- getLine
    putStrLn (weightHeightTell weight height) 


weightHeightTell :: String->String-> String  
weightHeightTell weight height  
    | (read weight / read height ^ 2) * 703 <= 18.5 = "You're underweight"  
    | (read weight / read height ^ 2) * 703 <= 25.0 = "You're supposedly normal"  
    | (read weight / read height ^ 2) * 703 <= 30.0 = "You're overweight"  
    | otherwise                   = "You're obese"  