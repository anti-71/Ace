DATA SEGMENT
    X DW 1122H
    Y DW 3344H
    Z DW ?
DATA ENDS

stack SEGMENT stack      
    DW 128 DUP(0)        
stack ENDS

CODE SEGMENT
    ASSUME CS:CODE, DS:DATA
START:
    MOV AX, DATA
    MOV DS, AX
    MOV AX, X      
    ADD AX, Y      
    MOV Z, AX      
    MOV AH, 4CH   
    INT 21H
CODE ENDS
    END START