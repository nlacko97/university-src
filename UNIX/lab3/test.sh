#!/bin/bash

(( $# < 1 )) && echo "Need one param" && exit 1

filename=$1

while true;do
	while true;do
		read char
		read filename
		break
	done <$filename
	printf $char
	[[ -z "$filename" ]] && echo "" && exit 0
done
