
(( $# < 1 )) && echo "Usage: $0 <string>" && exit 1

ls | grep -P ".{10}" | xargs -d"\n" rm

gcc prog2.c

./a.out $1
