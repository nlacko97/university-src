
main:: IO()
main = putStrLn "Hello World"

sum' :: Num a => [a] -> a
sum' [] = 0
sum' (x:xs) = x + sum' xs

sum'' :: Num a => [a] -> a
sum'' xs = my_foldr (+) 0 xs


product' :: Num a => [a] -> a
product' [] = 1
product' (x:xs) = x * product' xs

product'' :: Num a => [a] -> a
product'' = my_foldr(*) 1 


length' :: [a] -> Int
length' [] = 0
length' (_:xs) = 1 + length' xs

length'' :: [a] -> Int
length'' xs = foldl (\ acc x -> 1 + acc) 0 xs
length''' xs = foldr (\ _ -> (+1)) 0 xs


map' :: (a -> b) -> [a] -> [b]
map' _ [] = []
map' f (x:xs) = (f x) : (map' f xs)

-- map'' xs  = foldr (\ x acc -> (f x) : acc) [] xs

filter' :: (a -> Bool) -> [a] -> [a]
filter' pred [] = []
filter' pred (x:xs) | pred x    = x : filter' pred xs
                    | otherwise = filter' pred xs

my_foldr :: (a -> b -> b) -> b -> [a] -> b
my_foldr _ z [] = z
my_foldr f z (x:xs) = f x (my_foldr f z xs)

my_foldl :: (b -> a -> b) -> b -> [a] -> b
my_foldl _ acc [] = acc
my_foldl f acc (x:xs) = my_foldl f (f acc x) xs


pre_fac :: (Integer -> Integer) -> Integer -> Integer
pre_fac recCall = \ n -> if n <= 1 then 1 else n* recCall (n - 1) 

fix :: (t -> t) -> t
fix f = let x = f x in x

fac8 = fix pre_fac