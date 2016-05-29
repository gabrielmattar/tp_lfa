#!/bin/bash

if [ $# -eq 0 ]; then
    echo "Usar: ./run.sh entrada/[AFD's de entrada]"
    exit 1
fi

if [ ! -d "$dist" ]; then
	mkdir build &>/dev/null
	mkdir dist &>/dev/null
	javac src/**/*.java -d build/ &>/dev/null 

	cd build

	jar -cfe ../dist/GeradorLFA.jar tp_lfa.Main  **/*.class 
	cd ..
fi

rm -rf saidas
mkdir saidas
java -jar dist/GeradorLFA.jar $1 saidas/
STATUS=$?
if [ $STATUS -eq 1 ]; then 
    echo "Houve um erro no programa"
    exit 1
fi

cd saidas
for i in *.dot; do
	extension="${i##*.}"
	filename="${i%.*}"
	pdf='.pdf'
	saida=$filename$pdf
	dot -Tpdf -o $saida $i
done
echo "Arquivos de saida .dot gerados na pasta: saida/"
