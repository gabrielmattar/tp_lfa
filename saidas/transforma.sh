#!/bin/bash
for i in *.dot; do
	extension="${i##*.}"
	filename="${i%.*}"
	pdf='.pdf'
	saida=$filename$pdf
	dot -Tpdf -o $saida $i
done
