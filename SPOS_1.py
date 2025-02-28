'''
/***************************************************************************
 Name � sushil kailas sapkale
 Div - B-32
 Problem Statement :-
    Design suitable Data Structures and Implement Pass - I and Pass - II of a two pass
    assembler for pseudo machine. Implementation should consist of a few instructions from
    each category and few assembler directives. The Output of a Pass - I (Intermediate
    code file and symbol table) should be input for Pass - II.
***************************************************************************/
 
'''

IS = {
    "STOP" : "00",
    "ADD" : "01",
    "SUB" : "02",
    "MULT" : "03",
    "MOVER" : "04",
    "MOVEM" : "05",
    "COMP" : "06",
    "BC" : "07",
    "DIV" : "08",
    "READ" : "09",
    "PRINT" : "10"
    }

DL = {"DC" : "01",
      "DS" : "02"}
AD = {
    "START" : "01",
    "END" : "02",
    "ORIGIN" : "03",
    "EQU" : "04",
    "LTORG" : "05"
    }
REG = {
    "AREG" : "01",
    "BREG" : "02",
    "CREG" : "03",
    "DREG" : "04"
    }
COND = {
    "LT" : "1",
    "LE" : "2",
    "EQ" : "3",
    "GT" : "4",
    "GE" : "5",
    "ANY" : "6"
    }

loc_count = 0

f = open("file.txt",'r')
l = []
data = f.read()
data = data.strip()
m = []
l = data.splitlines()

flag = True
lag = 0
lit_tab = []
lit = [] # Total Literals
literals = 0
lit_cnt = 1
lit_count = 1
pool_tab = []

sym = {} # Symbols with addresses
sym_table = []
# cnt = 1
symbols = [] # Total Symbols

#to write in file
output = ""

#for symbols
for i in l:
    i = i.replace(","," ")
    m = i.split()
    
    if m[0] == "START":
        loc_count = int(m[1])
    if m[0] in IS:
        loc_count+=1
        if len(m) > 2 and '=' in m[2]:
            if not pool_tab:
                pool_tab.append(1)
            lit.append(m[2])   
            
    elif m[0] in AD:
        #LTORG
        if m[0] == "LTORG":
            lag+=1
            for i in lit:
                for j in lit_tab:
                    if i in j:
                        flag = False
                        break
                    else:
                        flag = True
                if flag:
                    lit_tab.append([i,loc_count])
                    loc_count+=1
                    literals+=1
            pool_tab.append(literals+1)
       
        if m[0] == 'ORIGIN':
            n = m[1].split("+")
            loc_count = int(sym[n[0]]) + int(n[1])
            
    else:
        if len(m) > 3 and '=' in m[3]:
            if not pool_tab:
                pool_tab.append(1)
            lit.append(m[3])
            
        if m[1] == "EQU":
            sym_table.append([m[0],sym[m[2]]])
            sym[m[0]] = sym[m[2]]
            symbols.append(m[0])
        
        else:
            sym_table.append([m[0],loc_count])
            sym[m[0]] = loc_count
            symbols.append(m[0])
          
             
        if m[1] in DL:
            if "'" not in m[2]:
                loc_count = loc_count + (int(m[2]) - 1)
        loc_count+=1
        

#for literals
for i in range(literals,len(lit)):    
    lit_tab.append([lit[i],loc_count-1])
    loc_count+=1
    


    
print(lit)
#for real
for i in l:
    i = i.replace(","," ")
    m = i.split()  
    
    if (len(m) > 1 and m[0] not in AD and m[1] not in AD) or m[0] == 'STOP':
        print(loc_count , end = "  ")
        output+=str(loc_count) + "  "
   
    if m[0] in AD:
        print("     (AD," + AD[m[0]] + ")", end = "  ")
        output+="\t (AD," + AD[m[0]] + ")" + "  "
        
        if m[0] == 'ORIGIN':
            n = m[1].split("+")
            loc_count = int(sym[n[0]]) + int(n[1])
            print("(C," + str(loc_count) + ")")
            output+="(C," + str(loc_count) + ")" + '\n'
            
                    
        if len(m) > 1 and m[1] != "ORIGIN" and m[0] != "ORIGIN":
            loc_count = int(m[1])
            print("(C," + m[1] + ")")
            output+="(C," + m[1] + ")" + "\n"
        
        if m[0] == 'LTORG':
            print()
            output+="\n"
            if m[0] == 'LTORG':
                for p in lit_tab:
                    while loc_count in p:
                        print(loc_count,"  (DL,01)   (C,",lit_count, ")",end = "\n",sep="")
                        output+=str(loc_count) + "  (DL,01)   (C," + str(lit_count) + ")" + "\n"
                        loc_count+=1
                        lit_count+=1
                
            
            
    elif m[0] in IS:    
        loc_count+=1
        print("(IS," + IS[m[0]] + ")" , end = "  ")
        output+="(IS," + IS[m[0]] + ")  "
        if m[0] == "BC":
            print(" " + COND[m[1]] , end = " "*8)
            output+=" " + COND[m[1]] + ' '*8
            print("(S," + str(symbols.index(m[2]) + 1) + ")")
            output+="(S," + str(symbols.index(m[2]) + 1) + ")" + "\n"
                
        if len(m) > 1 and m[1] in REG:
            print("(REG," + REG[m[1]] + ")", end = "  ")
            output+="(REG," + REG[m[1]] + ")  "
            if '=' in m[2]:
                print("(L," + str(lit_cnt) + ")")
                output+="(L," + str(lit_cnt) + ")" + "\n"
                lit_cnt+=1
            elif(len(m) > 1 and m[2] not in REG and m[2] not in COND):
                print("(S," + str(symbols.index(m[2]) + 1) + ")")
                output+="(S," + str(symbols.index(m[2]) + 1) + ")" + "\n"
                

        if(len(m) > 1 and m[1] not in REG and m[1] not in COND):
            print("(S," + str(symbols.index(m[1]) + 1) + ")")
            output+="(S," + str(symbols.index(m[1]) + 1) + ")" + "\n"
        
        if m[0] == "STOP" or m[1] == "STOP":
            output+="\n"
            print()
   
    else:
        loc_count+=1        
        if m[1] in DL:
            print("(DL," + DL[m[1]] + ")" , end = "  ")
            output+="(DL," + DL[m[1]] + ")" + "  "
            if "'" in m[2]:
                print("(C," + m[2].replace("'","") + ")")
                output+="(C," + m[2].replace("'","") + ")" + "\n"
            else:
                print("(C," + m[2] + ")")
                output+="(C," + m[2] + ")" + "\n"
                loc_count = loc_count + (int(m[2]) - 1)
                
        elif m[1] in IS:
            print("(IS," + IS[m[1]] + ")" , end = "  ")
            output+="(IS," + IS[m[1]] + ")  "
            if len(m) > 2 and m[2] in REG:
                print("(REG," + REG[m[2]] + ")", end = "  ")
                output+="(REG," + REG[m[2]] + ")  "

                if '=' in m[3]:
                    print("(L," + str(lit_cnt)  + ")")
                    output+="(L," + str(lit_cnt)  + ")"+'\n'
                    lit_cnt+=1
                elif(len(m) > 1 and m[3] not in REG and m[3] not in COND):
                    print("(S," + str(symbols.index(m[3]) + 1) + ")")
                    output+="(S," + str(symbols.index(m[3]) + 1) + ")" + "\n"
            if(len(m) > 2 and m[2] not in REG and m[1] not in COND):
                print("(S," + str(symbols.index(m[2]) + 1) + ")")
                output+="(S," + str(symbols.index(m[2]) + 1) + ")" + '\n'
        elif m[1] == "EQU":
            loc_count-=1
            
            print("     (AD," + AD[m[1]] + ")", end = "  ")
            output+="\t (AD," + AD[m[1]] + ")  " 
            if(len(m) > 1 and m[2] not in REG and m[2] not in COND):
                print("(S," + str(symbols.index(m[2]) + 1) + ")")
                output+="(S," + str(symbols.index(m[2]) + 1) + ")" + '\n'
        if m[0] == "STOP" or m[1] == "STOP":
            print()
            output+='\n'

print()
output+='\n'
for p in lit_tab:
    while loc_count in p:
        print(loc_count,"  (DL,01)   (C,",lit_count, ")",end = "\n",sep="")
        output+=str(loc_count) + "  (DL,1)   (C," + str(lit_count) + ')' + '\n' 
        loc_count+=1
        lit_count+=1                                       
        
print()

print(loc_count)
print(sym_table)
print(sym)
print()
print(lit_tab)
print(lit)
print(pool_tab)


print()

f1 = open('IC.txt','w')
for i in output:
    f1.write(i)
f1.close()

f2 = open('Sym_tab.txt','w')
for i in range(len(sym_table)):
    f2.write("{:8s}{:8s}{:8s}\n".format(str(i+1),sym_table[i][0],str(sym_table[i][1])))

f2.close()

f3 = open('Lit_tab.txt','w')
for i in range(len(lit_tab)):
    f3.write("{:8s}{:8s}{:8s}\n".format(str(i+1),lit_tab[i][0],str(lit_tab[i][1])))
f3.close()

f4 = open('Pool_tab.txt','w')
for i in range(len(pool_tab)):
    f4.write(str(pool_tab[i]) + "\n")
f4.close()
    
print()
    
f.close()

'''
=> file.txt :

	 START	 200
	 MOVER 	 AREG, ='5'
	 MOVEM   AREG  A
LOOP MOVER   AREG, A
	 MOVER   CREG, B
	 ADD     CREG, ='1'
	 MOVER 	 AREG  A
	 MOVER 	 CREG  B
	 MOVER 	 AREG  A
	 MOVER 	 CREG  B
	 MOVER 	 AREG  A
	 BC 	 ANY,  NEXT
	 LTORG
	 MOVER   AREG, A
NEXT SUB     AREG, ='1'
	 BC   	 LT, BACK
LAST STOP
	 ORIGIN  LOOP+2
	 MULT 	 CREG, B
	 ORIGIN  LAST+1
A    DS      1
BACK EQU     LOOP
B    DS      5
	 END	 



=> OUTPUT : 
     (AD,01)  (C,200)
200  (IS,04)  (REG,01)  (L,1)
201  (IS,05)  (REG,01)  (S,4)
202  (IS,04)  (REG,01)  (S,4)
203  (IS,04)  (REG,03)  (S,6)
204  (IS,01)  (REG,03)  (L,2)
205  (IS,04)  (REG,01)  (S,4)
206  (IS,04)  (REG,03)  (S,6)
207  (IS,04)  (REG,01)  (S,4)
208  (IS,04)  (REG,03)  (S,6)
209  (IS,04)  (REG,01)  (S,4)
210  (IS,07)   6        (S,2)
     (AD,05)  
211  (DL,01)   (C,1)
212  (DL,01)   (C,2)
213  (IS,04)  (REG,01)  (S,4)
214  (IS,02)  (REG,01)  (L,3)
215  (IS,07)   1        (S,5)
216  (IS,00)  
     (AD,03)  (C,204)
204  (IS,03)  (REG,03)  (S,6)
     (AD,03)  (C,217)
217  (DL,02)  (C,1)
     (AD,04)  (S,1)
218  (DL,02)  (C,5)
     (AD,02)  
223  (DL,01)   (C,3)

=> Lit_Tab :
1       ='5'    211     
2       ='1'    212     
3       ='1'    223     


=> Sym_Tab :
1       LOOP    202     
2       NEXT    214     
3       LAST    216     
4       A       217     
5       BACK    202     
6       B       219     


=> Pool_Tab :
1
3

'''
