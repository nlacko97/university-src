#!/bin/bash

a=`java Justify < input`
b=`java Justfy < input`
echo "--"
echo "$a"
echo -e "\n--------------------------------------------\n"
echo "--"
echo "$b"
echo ""

if [ "$a" = "$b" ]
then
	echo "matched"
else
	echo "not matched"
	diff  <(echo "$a" ) <(echo "$b")
fi
