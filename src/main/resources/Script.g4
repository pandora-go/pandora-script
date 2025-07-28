grammar Script;

block : statement*
 ;

statement
 : assignment SColon?
 | functionCall SColon?
 | ifStatement
 | whileStatement
 ;

assignment
 : Identifier Assign expression
 ;

functionCall
 : Identifier OParen expressionList? CParen #identifierFunctionCall
 | Print OParen expression CParen     #printFunctionCall
 | Assert OParen expression CParen    #assertFunctionCall
 ;

ifStatement
 : ifConditionBlock elseIfConditionBlock* elseBlock? End
 ;

ifConditionBlock
 : If expression Do block
 ;

elseIfConditionBlock
 : Else If expression Do block
 ;

elseBlock
 : Else Do block
 ;

whileStatement
 : While expression Do block End
 ;

expressionList
 : expression ( Comma expression )*
 ;

expression
 : '-' expression                                       #unaryMinusExpression
 | '!' expression                                       #notExpression
 | expression op=( '*' | '/' | '%' ) expression         #multExpression
 | expression op=( '+' | '-' ) expression               #addExpression
 | expression op=( '>=' | '<=' | '>' | '<' ) expression #compExpression
 | expression op=( '==' | '!=' ) expression             #eqExpression
 | expression '&&' expression                           #andExpression
 | expression '||' expression                           #orExpression
 | functionCall                                         #functionCallExpression
 | Number                                               #numberExpression
 | Bool                                                 #boolExpression
 | Null                                                 #nullExpression
 | Identifier                                           #identifierExpression
 | String                                               #stringExpression
 | OParen expression CParen                             #expressionExpression
 ;


Print    : 'print';
Assert   : 'assert';
If       : 'if';
Else     : 'else';
Return   : 'return';
While    : 'while';
Do       : 'do';
End      : 'end';
Null     : 'null';

Or       : '||';
And      : '&&';
Equals   : '==';
NEquals  : '!=';
GTEquals : '>=';
LTEquals : '<=';
Excl     : '!';
GT       : '>';
LT       : '<';
Add      : '+';
Subtract : '-';
Multiply : '*';
Divide   : '/';
Modulus  : '%';
OBrace   : '{';
CBrace   : '}';
OBracket : '[';
CBracket : ']';
OParen   : '(';
CParen   : ')';
SColon   : ';';
Assign   : '=';
Comma    : ',';
QMark    : '?';
Colon    : ':';

Bool
 : 'true'
 | 'false'
 ;

Number
 : Int ( '.' Digit* )?
 ;

Identifier
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

String
 : ["] ( ~["\r\n\\] | '\\' ~[\r\n] )* ["]
 | ['] ( ~['\r\n\\] | '\\' ~[\r\n] )* [']
 ;

Comment
 : ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip
 ;

Space
 : [ \t\r\n\u000C] -> skip
 ;

fragment Int
 : [1-9] Digit*
 | '0'
 ;

fragment Digit
 : [0-9]
 ;