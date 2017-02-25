module Interpreter
  (
    -- * Types
    Prog,

    -- * Functions
    evalRaw,
    evalAdt,
  ) where


-------------------------------------------------------------------------------
--------------------------------- The Expr ADT  -------------------------------
-------------------------------------------------------------------------------
data Expr = Add Expr Expr
          | Sub Expr Expr
          | Mult Expr Expr
          | Equal Expr Expr
          | Smaller Expr Expr
          | Symbol String
          | Value Int deriving (Show, Read)


-- [Optional] TODO Implement a parser for the Expr ADT.
--

valueof :: String -> [(String,Int)] -> Either String Int
valueof str [] = Left "Uninitialized variable"
valueof str ((variable,value):c) = 	if ( variable == str ) then Right value else  (valueof str c)

getEitherExpr :: Either String Int -> Int
getEitherExpr (Left _) = 0
getEitherExpr (Right _) = 1

getInt :: Either String Int -> Int
getInt (Right a) = a

getString :: Either String Int -> String
getString (Left a) = a

evalExpr :: [(String,Int)] -> Expr -> Either String Int

evalExpr c (Add a b) 
	| getEitherExpr x == 0 = x
	| getEitherExpr y == 0 = y
	| otherwise = Right ((getInt x) + (getInt y))
	where x = evalExpr c a;
		  y = evalExpr c b


evalExpr c (Sub a b)
	| getEitherExpr x == 0 = x
	| getEitherExpr y == 0 = y
	| otherwise = Right ((getInt x) - (getInt y))
	where x = evalExpr c a;
		  y = evalExpr c b
evalExpr c (Mult a b) 
	| getEitherExpr x == 0 = x
	| getEitherExpr y == 0 = y
	| otherwise = Right ((getInt x) * (getInt y))
	where x = evalExpr c a;
		  y = evalExpr c b

evalExpr c (Equal a b) 
	| getEitherExpr x == 0 = x
	| getEitherExpr y == 0 = y
	| otherwise = Right (if (getInt x) == (getInt y) then 1 else 0)
	where x = evalExpr c a;
		  y = evalExpr c b

evalExpr c (Smaller a b) 
	| getEitherExpr x == 0 = x
	| getEitherExpr y == 0 = y
	| otherwise = Right (if ((getInt x) < (getInt y)) then 1 else 0)
	where x = evalExpr c a;
		  y = evalExpr c b

evalExpr c (Symbol a) = valueof a c
evalExpr c (Value a) = Right a

-------------------------------------------------------------------------------
---------------------------------- The Prog ADT -------------------------------
-------------------------------------------------------------------------------
data Prog = Eq String Expr
          | Seq Prog Prog
          | If Expr Prog Prog
          | While Expr Prog
          | Return Expr deriving (Show, Read)

-- [Optional] TODO Implement a parser for the Prog ADT.
--

getEitherProg :: Either String [(String,Int)] -> Int
getEitherProg (Left _) = 0
getEitherProg (Right _ )= 1

getLeftProg :: Either String [(String,Int)] -> String
getLeftProg (Left x) = x

getRightProg :: Either String [(String,Int)] -> [(String,Int)]
getRightProg (Right x) = x


evalProg :: [(String,Int)] -> Prog -> Either String [(String,Int)]


evalProg c (Seq a b) 
	| getEitherProg x == 0 = Left (getLeftProg x)
	| getEitherProg y == 0 = Left (getLeftProg y)
	| otherwise = evalProg (getRightProg (evalProg c a)) b
	where x = evalProg c a;
		  y = evalProg ( getRightProg (evalProg c a)) b

						
evalProg c (If expr a b) 
	|getEitherExpr x == 0 = Left (getString x)
	|getEitherProg y == 0 = Left (getLeftProg y)
	|getEitherProg z == 0 = Left (getLeftProg z)
	|getInt x == 0 = z
	|otherwise = y 
	where x = evalExpr c expr;
		  y = evalProg c a;
		  z = evalProg c b
	
evalProg c (While expr a) 
	|getEitherExpr x == 0 = Left (getString x)
	|getEitherProg y == 0 = Left (getLeftProg y)
	|getInt x == 0 = Right c
	|otherwise =  evalProg (getRightProg y) (While expr a)
	where x = evalExpr c expr;
		  y = evalProg c a

evalProg c (Eq str expr) 
	|getEitherExpr x == 1 = Right ((str,getInt (evalExpr c expr)):c)
	|otherwise = Left (getString (evalExpr c expr))
	where x = evalExpr c expr

evalProg c (Return expr)  
	|getEitherExpr x == 1 = Right (("isRet",getInt (evalExpr c expr)):c)
	|otherwise = Left (getString (evalExpr c expr))
	where x = evalExpr c expr
							


	
-- [Optional] TODO The *parse* function.  It receives a String - the program in
-- a "raw" format and it could return *Just* a program as an instance of the
-- *Prog* data type if no parsing errors are encountered, or Nothing if parsing
-- failed.
--
-- This is composed with evalAdt to yield the evalRaw function.
parse :: String -> Maybe Prog
parse = undefined

-------------------------------------------------------------------------------
-------------------------------- The Interpreter ------------------------------
-------------------------------------------------------------------------------

-- TODO The *evalAdt* function.  It receives the program as an instance of the
-- *Prog* data type and returns an instance of *Either String Int*; that is,
-- the result of interpreting the program.
--
-- The result of a correct program is always an Int.  This is a simplification
-- we make in order to ease the implementation.  However, we wrap this Int with
-- a *Either String* type constructor to handle errors.  The *Either* type
-- constructor is defined as:
--
-- data Either a b = Left a | Right b
--
-- and it is generally used for error handling.  That means that a value of
-- *Left a* - Left String in our case - wraps an error while a value of *Right
-- b* - Right Int in our case - wraps a correct result (notice that Right is a
-- synonym for "correct" in English).
-- 
-- For further information on Either, see the references in the statement of
-- the assignment.
--
evalAdt :: Prog -> Either String Int

getContext prog =getRightProg (evalProg [] prog)

evalAdt = \p -> if (getEitherProg (evalProg [] p) == 0)
					then Left "Uninitialized variable"
					else if (getEitherExpr (valueof "isRet" (getContext p)) == 0) 
						then Left "Missing return" 
						else (valueof "isRet" (getContext p))



-- The *evalRaw* function is already implemented, but it relies on the *parse*
-- function which you have to implement.
--
-- Of couse, you can change this definition.  Only its name and type are
-- important.
evalRaw :: String -> Either String Int
evalRaw rawProg = case parse rawProg of
                    Just prog -> evalAdt prog
                    Nothing   -> Left "Syntax error"
