

main :: IO ()
main = putStrLn "Hello World"


-- the below definitions are the same. \ character means lambda --

fac :: Integer -> Integer
fac n = if n <= 1 then 1 else n * fac (n - 1)


fac2 :: Integer -> Integer
fac2 = \ n -> if n <= 1 then 1 else n * fac2 (n - 1)

fac3 :: Integer -> Integer
fac3 n | n <= 1     = 1
	   | otherwise  = n * fac3 (n - 1)

fac4 :: Integer -> Integer
fac4 0 = 1
fac4 n = n * fac4 (n - 1)

fac5 :: Integer -> Integer
fac5 input = case input of 
	0 -> 1
	n -> n * fac5 (n - 1)

if' :: Bool -> a -> a -> a
if' condition x y = case condition of
	True -> x
	False -> y

fac6 :: Integer -> Integer
fac6 n = if' (n <= 1) 1 {n * fac6 (n - 1)}

data MyBool = MyTrue | MyFalse

data AAA a = AAA a a a

data Pair a b = Pair a b

type Point = (Double, Double)
-- type Point a = Pair a a
type Radius = Double

data Shape = Triangle Point Point Point | Circle Point Radius | ...

data [a] = 	  [] 	| (:) 	 a : [a]
data List a = Empty | MkList a   (List a)









